package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ReelingEnchantment extends Enchantment{
    public ReelingEnchantment(){
        super(Enchantment.Rarity.COMMON, EnchantmentTarget.BOW, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
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
		return 12 + (level - 1) * 20;
	}

	@Override
	public int getMaxPower(int level) {
		return this.getMinPower(level) + 25;
	}
    @Override
    public int getMaxLevel() {
        return 1;
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
       double x=target.getPos().x-user.getPos().x;
       double z=target.getPos().z-user.getPos().z;
       if(target instanceof LivingEntity){
           ((LivingEntity) target).takeKnockback(level,x,z);
       }
    }
    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Enchantments.PUNCH;
    }
}
