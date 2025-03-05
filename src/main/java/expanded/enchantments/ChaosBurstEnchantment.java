package expanded.enchantments;

import java.util.List;

import org.joml.Math;

import expanded.enchantments.util.ParticleShapes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class ChaosBurstEnchantment extends Enchantment{
    public ChaosBurstEnchantment(){
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
         if(rand == 0 && target instanceof LivingEntity){
            World world = user.getWorld();
            ParticleShapes.burstShape(world, ParticleTypes.FLAME, 37, user.getPos(), 0.7);
            ParticleShapes.burstShape(world, ParticleTypes.SNOWFLAKE, 37, user.getPos(), 0.7);
            ParticleShapes.burstShape(world, ParticleTypes.FALLING_SPORE_BLOSSOM, 74, user.getPos(), 0.7);
            ParticleShapes.burstShape(world, ParticleTypes.ASH, 74, user.getPos(), 0.7);
            List<Entity> entities = world.getOtherEntities(user, Box.of(user.getPos(), 15, 15, 15));
            for (Entity hit : entities) {
                if(hit instanceof LivingEntity){
                    LivingEntity living = (LivingEntity)hit;
                    int rand2 = (int)(Math.random() * (4));
                    if(rand2==0){
                        StatusEffectInstance poison = new StatusEffectInstance(StatusEffects.POISON, 25*((level*4)), 0);
                        living.addStatusEffect(poison);
                    }
                    else if(rand2==1){
                        target.setFrozenTicks((80 * (level * 4) - 1));
                        StatusEffectInstance slow = new StatusEffectInstance(StatusEffects.SLOWNESS, (40 * ((level*4)-1)), 0,false, false, false);
                        living.addStatusEffect(slow);
                    }
                    else if(rand2==2){
                        StatusEffectInstance wither = new StatusEffectInstance(StatusEffects.WITHER, 20*((level*4)-1), 1);
                        living.addStatusEffect(wither);
                    }
                    else if(rand2==3){
                        living.setFireTicks(20*((level*4)-1));
                    }
                }
            }   
         }
    }
    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other) && other != Registers.FROST_BURST && other != Registers.POISON_BURST && other != Registers.WITHERING_BURST && other != Registers.EXPLOSIVE_BURST && other != Registers.FLAME_BURST;
    }
}
