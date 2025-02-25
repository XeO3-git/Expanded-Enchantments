package expanded.enchantments.mixin;

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
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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
    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo info) {
        ItemStack feet = this.getEquippedStack(EquipmentSlot.FEET);
        ItemStack head = this.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = this.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = this.getEquippedStack(EquipmentSlot.LEGS);

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
         int darkvisionEnchantment = EnchantmentHelper.getLevel(Registers.NIGHTVISION, head);
        if(darkvisionEnchantment>0 && !this.hasStatusEffect(StatusEffects.NIGHT_VISION)){
            StatusEffectInstance nightvis = new StatusEffectInstance(StatusEffects.NIGHT_VISION, 201, 1, true, false, false);
            this.addStatusEffect(nightvis);
        }

    }
    @Inject(method = "onKilledOther", at = @At("HEAD"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onKilledOther(ServerWorld world, LivingEntity other, CallbackInfoReturnable info) {
        ItemStack chest = this.getEquippedStack(EquipmentSlot.CHEST);
        double lifeStealLevel = EnchantmentHelper.getLevel(Registers.LIFESTEAL, chest);
        if(lifeStealLevel > 0){
            double hp = other.getAttributeBaseValue(EntityAttributes.GENERIC_MAX_HEALTH);
            System.out.println((float)((hp*lifeStealLevel)/10));
            this.heal(((float)(hp*lifeStealLevel)/10));
        }
    }
}