package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;

public class DrawWeightEnchantment extends Enchantment{
    public DrawWeightEnchantment(){
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.CROSSBOW, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
	public int getMinPower(int level) {
		return 1 + (level - 1) * 10;
	}

	@Override
	public int getMaxPower(int level) {
		return this.getMinPower(level) + 15;
	}
    @Override
    public int getMaxLevel() {
        return 5;
    }
    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        return(level*2)+2;
    }
}
