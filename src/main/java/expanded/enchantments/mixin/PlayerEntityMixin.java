package expanded.enchantments.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import expanded.enchantments.Registers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity{
    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    public abstract PlayerAbilities getAbilities();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    private static double prevHp;
    private boolean nightvis;
    private int timeLeft = 0;
    private UUID damId = UUID.randomUUID();
    private static double prevDam; 
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        ItemStack feet = this.getEquippedStack(EquipmentSlot.FEET);
        ItemStack head = this.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = this.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = this.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack mainHand = this.getEquippedStack(EquipmentSlot.MAINHAND);

        int healthBoostChest = EnchantmentHelper.getLevel(Registers.HEALTH_BOOST, chest);
        int healthBoostHead = EnchantmentHelper.getLevel(Registers.HEALTH_BOOST, head);
        int healthBoostLegs = EnchantmentHelper.getLevel(Registers.HEALTH_BOOST, legs);
        int healthBoostFeet = EnchantmentHelper.getLevel(Registers.HEALTH_BOOST, feet);
        if(healthBoostChest>0||healthBoostFeet>0||healthBoostHead>0||healthBoostLegs>0){
           this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(prevHp+healthBoostChest+healthBoostFeet+healthBoostHead+healthBoostLegs);
        }
        else{
            prevHp = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
        }
        
        int speedEnchant = EnchantmentHelper.getLevel(Registers.SWIFT_FEET, feet);
        if(speedEnchant!=0){
            StatusEffectInstance speed = new StatusEffectInstance(StatusEffects.SPEED, 20, speedEnchant-1, true, false, false);
            this.addStatusEffect(speed);
        }

        int darkvisionEnchantment=EnchantmentHelper.getLevel(Registers.NIGHTVISION, head);
        if(darkvisionEnchantment>0){
            this.nightvis = true;
           StatusEffectInstance nightvis = new StatusEffectInstance(StatusEffects.NIGHT_VISION, -1, 1, true, false, false);
            this.addStatusEffect(nightvis);
        }
        if(this.nightvis && darkvisionEnchantment==0){
            this.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }

        int soulSharpnessEnchant = EnchantmentHelper.getLevel(Registers.SOUL_SHARPNESS, mainHand);
        if(soulSharpnessEnchant > 0){
           int souls = mainHand.getNbt().getInt("souls");
           if(souls>0 && timeLeft !=0){
                timeLeft -=1;
           }
           else{
            if(souls>0){
                mainHand.getNbt().putInt("souls", souls-1);
                timeLeft = 6000;
            }
           }
        }
        else{
           prevDam = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getBaseValue();
           System.out.println(prevDam);
        }
    }
    @Inject(method = "onKilledOther", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onKilledOther(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> info) {
        ItemStack chest = this.getEquippedStack(EquipmentSlot.CHEST);
        double lifeStealLevel = EnchantmentHelper.getLevel(Registers.LIFESTEAL, chest);
        if(lifeStealLevel > 0){
            double hp = other.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH);
            this.heal(((float)(hp*lifeStealLevel)/10));
        }
        ItemStack mainHand = this.getEquippedStack(EquipmentSlot.MAINHAND);
         int lv = EnchantmentHelper.getLevel(Registers.SOUL_SHARPNESS, mainHand);
           if(lv != 0){
                NbtCompound nbt = mainHand.getNbt();
                if(nbt.contains("souls")){
                    timeLeft = 6000;
                    int souls= nbt.getInt("souls");
                    if(souls<=20){
                        nbt.putInt("souls", souls+1);
                        mainHand.setNbt(nbt);
                            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).removeModifier(damId);
                            EntityAttributeModifier damMod = new EntityAttributeModifier(damId, "damMod", souls, Operation.ADDITION);
                            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addPersistentModifier(damMod);
                    }
                }
                else{
                    nbt.putInt("souls", 1);
                    mainHand.setNbt(nbt);
                    timeLeft = 6000;
                    if(this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).getModifier(damId)==null){
                            EntityAttributeModifier damMod = new EntityAttributeModifier(damId, "damMod", 1, Operation.ADDITION);
                            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addPersistentModifier(damMod);
                        }
                    else{
                            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).removeModifier(damId);
                            EntityAttributeModifier damMod = new EntityAttributeModifier(damId, "damMod", 1, Operation.ADDITION);
                            this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addPersistentModifier(damMod);
                    }
                }

           }
    }
}