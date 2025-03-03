package expanded.enchantments.mixin;


import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import expanded.enchantments.Registers;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
@Mixin(ArrowEntity.class)
public abstract class ArrowEntityMixin extends PersistentProjectileEntity{

        protected ArrowEntityMixin(EntityType<? extends ArrowEntity> entityType, World world) {
            super(entityType, world);
        }
        private LivingEntity target;
        private int steps = 10;
        private Vec3d toAdd;
        private boolean changed = false;
        @Inject(method = "tick", at = @At("TAIL"))
        private void tick(CallbackInfo info) {
        if(this.getOwner() instanceof LivingEntity && !this.inGround){
            LivingEntity owner = (LivingEntity)this.getOwner();
            int lvHeatSeeking = EnchantmentHelper.getLevel(Registers.HEATSEEKING, owner.getMainHandStack());
            int lvWeightlessArrows = EnchantmentHelper.getLevel(Registers.WEIGHTLESS_ARROWS, owner.getMainHandStack());
            if(lvHeatSeeking>0){
                if(target == null){
                    Box box = Box.of(this.getPos(), Math.pow(2, lvHeatSeeking)+2, Math.pow(2, lvHeatSeeking)+2, Math.pow(2, lvHeatSeeking)+2);
                    List<Entity> near = this.getWorld().getOtherEntities(owner, box);
                    List<LivingEntity> targetable = new ArrayList<>();
                    for (Entity entity : near) {
                        if(isTargerable(entity)){
                            targetable.add((LivingEntity)entity);
                        }
                    }
                    if(!targetable.isEmpty()){
                        target = this.getWorld().getClosestEntity(targetable, TargetPredicate.DEFAULT, owner, this.getX(), this.getY(), this.getZ());   
                    }
                }
                else if(!changed){
                    Vec3d direction = new Vec3d((target.getX()-this.getX()), (target.getEyeY()-this.getY()), (target.getZ()-this.getZ()));
                    double length = this.getVelocity().length();
                    Vec3d nVeloc = direction.normalize().multiply(length);
                    this.setVelocity(nVeloc);
                    this.setNoGravity(true);
                    toAdd = new Vec3d(nVeloc.x/steps, nVeloc.y/steps, nVeloc.z/steps);
                    this.setVelocity(toAdd);
                    changed = true;
                }
                else if(changed && toAdd != toAdd.multiply(steps) && this.getVelocity().length()<=toAdd.multiply(steps).length()){
                    this.addVelocity(toAdd);   
                }
            }
            if(lvWeightlessArrows>0 && !this.hasNoGravity()){
                this.setNoGravity(true);
            }
        }
    }
    private boolean isTargerable(Entity entity){
        Vec3d directionTo = new Vec3d(entity.getX()-this.getX(), entity.getEyeY()-entity.getY(), entity.getZ()-this.getZ());
        double angle = Math.acos((directionTo.dotProduct(this.getVelocity())/(directionTo.length()*this.getVelocity().length())));
        if(entity instanceof LivingEntity && !(entity instanceof EndermanEntity) && angle<Math.PI/2){
            return true;
        }
        else{
            return false;
        }
    }
}
