package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class LaunchingEnchantment extends Enchantment{
    public LaunchingEnchantment(){
        super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.BOW, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
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
        return 2;
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
       target.addVelocity(0, (((double)level)/5), 0);
    }
}
