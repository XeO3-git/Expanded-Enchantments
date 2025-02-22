package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World.ExplosionSourceType;

public class ExplodingArrowsEnchantment extends Enchantment{
    public ExplodingArrowsEnchantment(){
            super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.BOW, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
	public int getMinPower(int level) {
		return 40*level;
	}

	@Override
	public int getMaxPower(int level) {
		return this.getMinPower(level) + 15;
	}
    @Override
    public int getMaxLevel() {
        return 3;
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        boolean fire = false;
        if (level>1) {
            fire=true;
        }
        target.getWorld().createExplosion(target, target.getX(), target.getY(), target.getZ(), 0.6F+(level-2), fire, ExplosionSourceType.BLOCK);
    }

}
