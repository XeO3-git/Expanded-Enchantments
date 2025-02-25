package expanded.enchantments.mixin;

import java.util.List;
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
import net.minecraft.entity.Entity;
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
import net.minecraft.util.math.Box;
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
           int souls = mainHand.getNbt().getInt("Amount");
           if(souls>0 && timeLeft !=0){
                timeLeft -=1;
           }
           else{
            if(souls>0){
                mainHand.getNbt().putInt("Amount", souls-1);
                timeLeft = 6000;
            }
           }
        }

        int omniscienceEnchantment = EnchantmentHelper.getLevel(Registers.OMNISCIENCE, head);
        if(omniscienceEnchantment>0){
           List<Entity> near = this.getWorld().getOtherEntities(this, Box.of(this.getPos(), 128, 128, 128));
           for (Entity in : near) {
            if(in instanceof LivingEntity){
                LivingEntity living = (LivingEntity)in;
                StatusEffectInstance glow = new StatusEffectInstance(StatusEffects.GLOWING, 2, 1, true, false, false);
                living.addStatusEffect(glow);
            }
           }
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
            if(nbt.contains("Amount") && nbt.contains("Name") && nbt.contains("Operation") && nbt.contains("UUID")){
                timeLeft = 6000;
                int souls= nbt.getInt("Amount");
                if(souls<=20){
                    mainHand.getAttributeModifiers(EquipmentSlot.MAINHAND).remove(EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier.fromNbt(nbt));//This doesnt seem to remove the attribute modifiers...
                    nbt.putInt("Amount", souls+1);
                    mainHand.setNbt(nbt);
                    mainHand.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier.fromNbt(nbt), EquipmentSlot.MAINHAND);
                }
            }
            else{
                nbt.putInt("Amount", 1);
                nbt.putInt("Operation", Operation.ADDITION.getId());
                nbt.putString("Name", "SoulsDam");
                nbt.putUuid("UUID", UUID.randomUUID());
                mainHand.setNbt(nbt);
                timeLeft = 6000;
                mainHand.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, EntityAttributeModifier.fromNbt(nbt), EquipmentSlot.MAINHAND);
            }
        }
    }
}