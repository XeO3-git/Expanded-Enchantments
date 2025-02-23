package expanded.enchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class CourageEnchantment extends Enchantment {
		 public CourageEnchantment(){
            super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_LEGS, new EquipmentSlot[] {EquipmentSlot.LEGS});
    }

	@Override
	public int getMinPower(int level) {
		return level * 15;
	}

	@Override
	public int getMaxPower(int level) {
		return this.getMinPower(level) + 50;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}


	@Override
	public int getMaxLevel() {
		return 3;
	}
	@Override
	public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
		StatusEffectInstance strength = new StatusEffectInstance(StatusEffects.STRENGTH, 300, level+2);
		user.addStatusEffect(strength);
	}
	@Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Registers.COWARDICE;
    }
}