package expanded.enchantments;

import java.util.List;

import org.joml.Math;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class WitherBurstEnchantment extends Enchantment{
    public WitherBurstEnchantment(){
            super(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
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
         int rand = (int)(Math.random() * (10/level)-1);
         if(rand == 0){
            World world = user.getWorld();
            List<Entity> entities = world.getOtherEntities(user, Box.of(user.getPos(), 15, 15, 15));
            for (Entity hit : entities) {
                if(hit instanceof LivingEntity){
                    StatusEffectInstance wither = new StatusEffectInstance(StatusEffects.WITHER, 25*((level*4)-1), 0);
                    LivingEntity living = (LivingEntity)hit;
                    living.addStatusEffect(wither);
                }
            }



         }
    }
    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Registers.FROST_BURST && other != Registers.CHAOS_BURST && other != Registers.WITHERING_BURST && other != Registers.EXPLOSIVE_BURST && other != Registers.FLAME_BURST;
    }
}
