package expanded.enchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;

import expanded.enchantments.Registers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
@Mixin(CrossbowItem.class)
public abstract class CrossbowMixin {
    
    @ModifyReturnValue(method = "getPullTime", at = @At("RETURN"))
    private static int editDraw(int prev, @Local ItemStack stack){
        int lv = EnchantmentHelper.getLevel(Registers.DRAW_WEIGHT, stack);
        if(lv!=0){
            return prev+lv*15;
        }
        else return(prev);
    }
    @ModifyReturnValue(method = "getSpeed", at=@At("RETURN"))
     private static float editSpeed(float prev, @Local ItemStack stack){
        float lv = EnchantmentHelper.getLevel(Registers.DRAW_WEIGHT, stack);
        if(lv!=0){
            return prev+0.85F;
        }
        else return(prev);
    }
    @Inject(at = @At("HEAD"), method = "postShoot", locals = LocalCapture.CAPTURE_FAILHARD)
	private static void pulling(World world, LivingEntity entity, ItemStack stack, CallbackInfo info) {
		int lv = EnchantmentHelper.getLevel(Registers.PULLING, stack);
		if(lv != 0){
			entity.takeKnockback(4*lv, -entity.getRotationVector().x, -entity.getRotationVector().z);
		}
	}
    @Inject(at = @At("HEAD"), method = "postShoot", locals = LocalCapture.CAPTURE_FAILHARD)
	private static void recoil(World world, LivingEntity entity, ItemStack stack, CallbackInfo info) {
		int lv = EnchantmentHelper.getLevel(Registers.RECOIL, stack);
        if(lv>0){
		    entity.takeKnockback(lv+1, entity.getRotationVector().x, entity.getRotationVector().z);
        }
	}
}