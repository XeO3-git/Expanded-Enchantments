package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class ThunderGodEnchantment extends Enchantment{
    public ThunderGodEnchantment(){
            super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST, new EquipmentSlot[] {EquipmentSlot.CHEST});
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
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        int rand = (int)(Math.random() * (level*4));
        if(rand == 1){
            World world = user.getWorld();
            LightningEntity bolt = new LightningEntity(EntityType.LIGHTNING_BOLT,  world);
            bolt.setPosition(target.getPos());
            world.spawnEntity(bolt);
        }
    }
}
