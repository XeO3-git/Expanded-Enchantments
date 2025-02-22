package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class ChaosAspectEnchantment extends Enchantment{
    public ChaosAspectEnchantment(){
            super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
	public int getMinPower(int level) {
		return 15 + 20 * (level - 1);
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
        if(target instanceof LivingEntity){
            applyEffect(user, (LivingEntity)target, level);
        }
    }
    private void applyEffect(LivingEntity user, LivingEntity target, int level){
        int rand = (int)(Math.random() * (4));
        if(rand==0){
            StatusEffectInstance poison = new StatusEffectInstance(StatusEffects.POISON, 25*((level*4)), 0);
            target.addStatusEffect(poison);
        }
        else if(rand==1){
            target.setFrozenTicks((80 * (level * 4) - 1));
            StatusEffectInstance slow = new StatusEffectInstance(StatusEffects.SLOWNESS, (40 * ((level*4)-1)), 0,false, false, false);
            target.addStatusEffect(slow);
        }
        else if(rand==2){
            StatusEffectInstance poison = new StatusEffectInstance(StatusEffects.WITHER, 20*((level*4)-1), 1);
            target.addStatusEffect(poison);
        }
        else if(rand==3){
            target.setFireTicks(20*((level*4)-1));
        }

    }
    @Override
    protected boolean canAccept(Enchantment other) {
       return super.canAccept(other) && other != Enchantments.FIRE_ASPECT && other != Registers.FROST_ASPECT && other != Registers.POISON_ASPECT && other != Registers.WITHER_ASPECT;
    }
}
