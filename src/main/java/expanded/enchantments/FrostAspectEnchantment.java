package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class FrostAspectEnchantment extends Enchantment {
    public FrostAspectEnchantment() {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
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
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target instanceof LivingEntity && !target.isOnFire()) {
            LivingEntity livingTarget = (LivingEntity) (target);
            livingTarget.setFrozenTicks((80 * (level * 4) - 1));
            StatusEffectInstance slow = new StatusEffectInstance(StatusEffects.SLOWNESS, (40 * (level * 4)-1), 0,
                    false, false, false);
            livingTarget.addStatusEffect(slow);
        }
    }
    @Override
    protected boolean canAccept(Enchantment other) {
       return super.canAccept(other) && other != Enchantments.FIRE_ASPECT && other != Registers.POISON_ASPECT && other != Registers.CHAOS_ASPECT && other != Registers.WITHER_ASPECT;
    }
}
