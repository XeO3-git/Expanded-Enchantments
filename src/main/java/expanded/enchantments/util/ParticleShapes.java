package expanded.enchantments.util;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleShapes {
    public static void burstShape(World world, ParticleEffect particle, int num, Vec3d pos, double veloc){
        for(int i = 0; i<num; i++){
            if(!world.isClient){
                ServerWorld serv = (ServerWorld)world;
                double randangle1 = Math.random()*Math.PI;
                double randangle2 = Math.random()*Math.PI;
                double randangle3 = Math.random()*Math.PI;
                serv.spawnParticles(particle, pos.x, pos.y, pos.z,1, randangle1, randangle2, randangle3, veloc);
            }
        }
    }
}
