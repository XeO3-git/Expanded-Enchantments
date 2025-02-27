package expanded.enchantments.mixin;

import java.util.List;

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
    private boolean stepping;
    private boolean nightvis;
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        ItemStack feet = this.getEquippedStack(EquipmentSlot.FEET);
        ItemStack head = this.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = this.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = this.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack mainHand = this.getEquippedStack(EquipmentSlot.MAINHAND);
        //healthBoost
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
        //speed
        int speedEnchant = EnchantmentHelper.getLevel(Registers.SWIFT_FEET, feet);
        if(speedEnchant!=0){
            StatusEffectInstance speed = new StatusEffectInstance(StatusEffects.SPEED, 20, speedEnchant-1, true, false, false);
            this.addStatusEffect(speed);
        }
        //darkvision
        int darkvisionEnchantment=EnchantmentHelper.getLevel(Registers.NIGHTVISION, head);
        if(darkvisionEnchantment>0){
            this.nightvis = true;
           StatusEffectInstance nightvis = new StatusEffectInstance(StatusEffects.NIGHT_VISION, -1, 1, true, false, false);
            this.addStatusEffect(nightvis);
        }
        if(this.nightvis && darkvisionEnchantment==0){
            this.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
        //omniscience
        int omniscienceEnchantment = EnchantmentHelper.getLevel(Registers.OMNISCIENCE, head);
        if(omniscienceEnchantment>0){
           List<Entity> near = this.getWorld().getOtherEntities(this, Box.of(this.getPos(), 64, 64, 64));
           for (Entity in : near) {
            if(in instanceof LivingEntity){
                LivingEntity living = (LivingEntity)in;
                StatusEffectInstance glow = new StatusEffectInstance(StatusEffects.GLOWING, 2, 1, true, false, false);
                living.addStatusEffect(glow);
            }
           }
        }
        //stepping
        int stepHeightEnchantment = EnchantmentHelper.getLevel(Registers.STEPPING, feet);//fix the bug where you will be able to continue to step up blocks after taking off boots
        if(stepHeightEnchantment>0){
            this.setStepHeight(stepHeightEnchantment);
            stepping = true;
        }
        else{
            if(stepping){
                this.setStepHeight(0.6F);
            }
        }

    }
    @Inject(method = "onKilledOther", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onKilledOther(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> info) {
        //lifeSteal
        ItemStack chest = this.getEquippedStack(EquipmentSlot.CHEST);
        double lifeStealLevel = EnchantmentHelper.getLevel(Registers.LIFESTEAL, chest);
        if(lifeStealLevel > 0){
            double hp = other.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH);
            this.heal(((float)(hp*lifeStealLevel)/10));
        }
        //soulSharpness
        ItemStack mainHand = this.getEquippedStack(EquipmentSlot.MAINHAND);
        int lv = EnchantmentHelper.getLevel(Registers.SOUL_SHARPNESS, mainHand);
        if(lv != 0){
            NbtCompound nbt = mainHand.getNbt();
            if(nbt.contains("souls")){
                int souls= nbt.getInt("souls");
                if(souls<=11){
                    nbt.putInt("souls", souls+1);
                }
                mainHand.setNbt(nbt);
            }
            else{
                nbt.putInt("souls", 1);
                mainHand.setNbt(nbt);
            }
        }
    }
}