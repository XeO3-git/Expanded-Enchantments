package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class HeatSeekingEnchantment extends Enchantment{
    public HeatSeekingEnchantment(){
        super(Enchantment.Rarity.UNCOMMON, EnchantmentTarget.BOW, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        if(stack.isOf(Items.CROSSBOW) || stack.isOf(Items.BOW)){
            return true;
        }
        else{
            return false;
        }
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
