package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class WitherAspectEnchantment extends Enchantment{
    public WitherAspectEnchantment(){
            super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
	public int getMinPower(int level) {
		return 10 + 20 * (level - 1);
	}

	@Override
	public int getMaxPower(int level) {
		return super.getMinPower(level) + 50;
	}
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if(target instanceof LivingEntity){
           LivingEntity livingTarget=(LivingEntity)(target);
           StatusEffectInstance poison = new StatusEffectInstance(StatusEffects.WITHER, 20*(level*4), 1);
            livingTarget.addStatusEffect(poison);
        }
    }
    @Override
    protected boolean canAccept(Enchantment other) {
       return super.canAccept(other) && other != Enchantments.FIRE_ASPECT && other != Registers.FROST_ASPECT && other != Registers.CHAOS_ASPECT && other != Registers.POISON_ASPECT;
    }
}
