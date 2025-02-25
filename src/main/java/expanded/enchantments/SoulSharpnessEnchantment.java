package expanded.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
public class SoulSharpnessEnchantment extends Enchantment{
    public SoulSharpnessEnchantment(){
            super(Enchantment.Rarity.VERY_RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMaxLevel() {
        return 1;
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
}
