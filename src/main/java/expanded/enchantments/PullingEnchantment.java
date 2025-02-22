package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class PullingEnchantment extends Enchantment{
    public PullingEnchantment(){
            super(Enchantment.Rarity.RARE, EnchantmentTarget.CROSSBOW, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
	public int getMinPower(int level) {
		return level * 25;
	}
	@Override
	public int getMaxPower(int level) {
		return this.getMinPower(level) + 50;
	}
    @Override
    public int getMaxLevel() {
        return 2;
    }
    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Registers.RECOIL && other != Registers.REELING;
    }
}
