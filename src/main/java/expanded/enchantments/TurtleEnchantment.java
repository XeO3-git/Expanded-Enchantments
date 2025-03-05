package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TurtleEnchantment extends Enchantment{
    public TurtleEnchantment(){
            super(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }
    @Override
	public int getMinPower(int level) {
		return 10 + 20 * (level - 1);
	}

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if(stack.isOf(Items.SHIELD)){
            return true;
        }
        else{
            return false;
        }
    }

	@Override
	public int getMaxPower(int level) {
		return super.getMinPower(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}
}
