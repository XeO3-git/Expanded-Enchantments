package expanded.enchantments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import expanded.enchantments.Registers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(at = @At("HEAD"), method = "onDeath", locals = LocalCapture.CAPTURE_FAILHARD)
	private static void death(DamageSource damageSource, CallbackInfo info) {
        if(damageSource.getAttacker() instanceof PlayerEntity){
           PlayerEntity player = (PlayerEntity)damageSource.getAttacker();
           ItemStack item = player.getMainHandStack();
           int lv = EnchantmentHelper.getLevel(Registers.SOUL_SHARPNESS, item);
           if(lv != 0){
                NbtCompound nbt = item.getNbt();
                if(nbt.contains("souls")){
                    int souls= nbt.getInt("souls");
                    if(souls<21){
                      nbt.putInt("souls", souls+1);
                      item.setNbt(nbt);
                    }
                }
                else{
                    nbt.putInt("souls", 1);
                    item.setNbt(nbt);
                }
           }
        }
	}
}