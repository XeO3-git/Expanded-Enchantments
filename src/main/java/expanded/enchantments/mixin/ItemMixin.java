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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(at = @At("HEAD"), method = "inventoryTick", locals = LocalCapture.CAPTURE_FAILHARD)
	private static void init(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo info) {
        int lv = EnchantmentHelper.getLevel(Registers.SOUL_SHARPNESS, stack);
        if(lv!=0){
            NbtCompound nbt = stack.getNbt();
            if(nbt.contains("souls") && nbt.contains("timeleft")){
                if(nbt.getInt("souls")>0){
                    int time = nbt.getInt("timeleft");
                    time-=1;
                    nbt.putInt("timeleft", time);
                    if(time==0){
                        time = 6000;
                        nbt.putInt("souls", nbt.getInt("souls")-1);   
                        nbt.putInt("timeleft", time);   
                        stack.setNbt(nbt);
                    }
                }
                else{
                    if(entity instanceof LivingEntity){
                        LivingEntity alive = (LivingEntity)entity;
                        StatusEffectInstance weak = new StatusEffectInstance(StatusEffects.WEAKNESS, 20);
                        alive.addStatusEffect(weak);
                    }
                }
            }
            if(!nbt.contains("timeleft") && nbt.contains("souls")){
                nbt.putInt("timeleft", 6000);
                stack.setNbt(nbt);
            }
        }
	}
}