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
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World.ExplosionSourceType;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin{
    boolean exploded = false;
    @Inject(at = @At("TAIL"), method = "onBlockHit", locals = LocalCapture.CAPTURE_FAILHARD)
	private void hitBlock(BlockHitResult blockHitResult, CallbackInfo info) {
       if(!exploded){
         Entity owner = ((PersistentProjectileEntity)(Object)this).getOwner();
        if(owner instanceof LivingEntity){
            LivingEntity living = (LivingEntity)owner;
            int lv = EnchantmentHelper.getLevel(Registers.EXPLODING_ARROWS, living.getMainHandStack());
            if(lv !=0){
                boolean fire = false;
                if(lv>1){
                    fire = true;
                }
                living.getWorld().createExplosion(living, blockHitResult.getPos().x, blockHitResult.getPos().y, blockHitResult.getPos().z, 0.6F+(lv-2), fire, ExplosionSourceType.BLOCK);
                exploded = true;
            }
        }
       }
    }
    
}
