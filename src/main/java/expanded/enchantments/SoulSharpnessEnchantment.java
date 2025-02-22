package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
public class SoulSharpnessEnchantment extends Enchantment{
    public SoulSharpnessEnchantment(){
            super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMaxLevel() {
        return 1;
    }
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }
    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(target instanceof LivingEntity){
            ItemStack stack = user.getMainHandStack();
            int souls = stack.getNbt().getInt("souls");
            LivingEntity livingTarget = (LivingEntity) (target);
            if (!target.isOnFire()) {
                livingTarget.setFrozenTicks((40 * souls));
                StatusEffectInstance slow = new StatusEffectInstance(StatusEffects.SLOWNESS, (60 * souls), souls/5,
                        false, false, false);
                livingTarget.addStatusEffect(slow);
            } 
            StatusEffectInstance wither = new StatusEffectInstance(StatusEffects.WITHER, (60 * (souls-1)), souls/4);
            livingTarget.addStatusEffect(wither);
        }  
    }
    @Override
     protected boolean canAccept(Enchantment other) {
       return super.canAccept(other) && other != Enchantments.FIRE_ASPECT && other != Registers.POISON_ASPECT && other != Registers.CHAOS_ASPECT && other != Registers.WITHER_ASPECT && other != Registers.FROST_ASPECT;
    }
    @Override
    public boolean isTreasure() {
        return true;
    }
}
