package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class SwiftFeetEnchantment extends Enchantment{
    public SwiftFeetEnchantment(){
            super(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR_FEET, new EquipmentSlot[] {EquipmentSlot.FEET});
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
		return 4;
	}
}
