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
    private int lvHeatSeeking;
    private int lvWeightlessArrows;
    private LivingEntity owner;
    private Vec3d prevVeloc;
    private boolean changed;
    @Inject(method = "initFromStack", at = @At("TAIL"))
    private void init(CallbackInfo info) {
        if(this.getOwner() instanceof LivingEntity){
            owner = (LivingEntity)this.getOwner();
            lvHeatSeeking = EnchantmentHelper.getLevel(Registers.HEATSEEKING, owner.getMainHandStack());
            lvWeightlessArrows = EnchantmentHelper.getLevel(Registers.WEIGHTLESS_ARROWS, owner.getMainHandStack());
            Box box = Box.of(this.getPos(), 128,128, 128);
            List<Entity> near = this.getWorld().getOtherEntities(owner, box);
            double maxAngle = lvHeatSeeking==1 ? Math.PI/12 : lvHeatSeeking == 2 ? Math.PI/8 : Math.PI/4;  
            target = getTarget(near, maxAngle/8, maxAngle, owner);//recursive function that returns the closest target within a small angle of the owner
        }
    }
    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo info) {
        if(!changed){
            prevVeloc = this.getVelocity();
        }
        if(target != null && target.isAlive()){
            Vec3d veloc = this.getVelocity();
            Vec3d direction = new Vec3d(target.getX() - this.getX(),target.getEyeY() - this.getY(),target.getZ() - this.getZ()).normalize();
            if(angleBetween(veloc, direction)>0.01){
                double steps = lvHeatSeeking == 4 ? 1 : 30/((double)lvHeatSeeking);
                double angle = angleBetween(veloc, direction)/steps;
                Vec3d vNorm = veloc.normalize();
                // Compute the rotation axis
                Vec3d rotationAxis = vNorm.crossProduct(direction);
                rotationAxis = rotationAxis.normalize();
                // Compute each term of Rodrigues' formula.
                Vec3d term1 = veloc.multiply(Math.cos(angle));  // v * cos(theta)
                Vec3d term2 = rotationAxis.crossProduct(veloc).multiply(Math.sin(angle));  // (k x v) * sin(theta)
                Vec3d term3 = rotationAxis.multiply(rotationAxis.dotProduct(veloc) * (1 - Math.cos(angle))); // k (k dot v) * (1 - cos(theta))
                Vec3d nVeloc = term1.add(term2).add(term3);
                nVeloc = (nVeloc.normalize()).multiply(prevVeloc.length());
                changed = true;
                this.setVelocity(nVeloc); 
            }
            this.setNoGravity(true);
        }
        if(lvWeightlessArrows>0 && lvHeatSeeking == 0){
            this.setNoGravity(true);
            this.setVelocity(prevVeloc);
            changed = true;
        }
    }
    private LivingEntity getTarget(List<Entity> near, double startAngle, double endAngle, LivingEntity owner){
        List<LivingEntity> targetable = new ArrayList<>();
        for (Entity entity : near) {
            Vec3d direction = new Vec3d((entity.getX()-owner.getX()), (entity.getEyeY()-owner.getEyeY()), (entity.getZ()-owner.getZ()));
            double angle = angleBetween(owner.getRotationVector(), direction);//angle between the owner and the entity
            if(entity instanceof LivingEntity && !(entity instanceof EndermanEntity) && angle<startAngle && owner.canSee(entity)){
                targetable.add((LivingEntity)entity);
            }
        }
        if(!targetable.isEmpty()){
            return this.getWorld().getClosestEntity(targetable, TargetPredicate.DEFAULT, owner, this.getX(), this.getY(), this.getZ());   
        }
        else if (startAngle != endAngle){
           return getTarget(near, startAngle*2, endAngle, owner);
        }
        else{
            return null;
        }
    }
    private double angleBetween(Vec3d vec1, Vec3d vec2){
        return Math.acos((vec1.dotProduct(vec2)/(vec1.length()*vec2.length())));
    }
}

/*      else if(!changed){
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
                } */
