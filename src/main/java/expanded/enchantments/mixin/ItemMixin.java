package expanded.enchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import expanded.enchantments.Registers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
@Mixin(Item.class)
public abstract class ItemMixin {
    private static int timeLeft=6000;
    @Inject(at = @At("HEAD"), method = "inventoryTick", locals = LocalCapture.CAPTURE_FAILHARD)
	private static void tick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo info) {
    System.out.println(timeLeft);
    int soulSharpnessEnchant = EnchantmentHelper.getLevel(Registers.SOUL_SHARPNESS, stack);
        if(soulSharpnessEnchant > 0){
           int souls = stack.getNbt().getInt("souls");
           if(souls>0 && timeLeft > 0){
                timeLeft -=1;
           }
           else{
            if(souls>0){
                stack.getNbt().putInt("souls", souls-1);
                timeLeft = 6000;
            }
            else{
                if(entity instanceof LivingEntity){
                    StatusEffectInstance weak = new StatusEffectInstance(StatusEffects.WEAKNESS, 2, 0, true, false, false);
                    ((LivingEntity)entity).addStatusEffect(weak);
                }
            }
           }
        }
	}
}