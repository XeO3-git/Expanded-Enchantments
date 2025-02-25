package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class OmniscienceEnchantment extends Enchantment{
    public OmniscienceEnchantment(){
            super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[] {EquipmentSlot.HEAD});
    }
    @Override
	public int getMinPower(int level) {
		return 40 * (level);
	}

	@Override
	public int getMaxPower(int level) {
		return super.getMinPower(level) + 50;
	}
    @Override
    public int getMaxLevel() {
        return 1;
    }
    @Override
    public boolean isTreasure() {
        return false;
    }
}
