package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
public class SoulSharpnessEnchantment extends Enchantment{
    private int souls;
    public SoulSharpnessEnchantment(){
            super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMaxLevel() {
        return 1;
    }
    @Override
    public float getAttackDamage(int level, EntityGroup group) {
        if(souls>0){
            return souls;
        }
        else{        
            return 0;
        }
    }
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return true;
    }
    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }
    @Override
     protected boolean canAccept(Enchantment other) {
       return super.canAccept(other) && other != Enchantments.SHARPNESS;
    }
    @Override
    public boolean isTreasure() {
        return true;
    }
    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        souls = user.getMainHandStack().getNbt().getInt("souls");
    }
}
