package sheepindev.projectares.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class FreezingParticleEmitter extends ParticleEmitter {
    private static final double RADIUS = 1;
    private static final int SLICES = 16;
    private static final double YSTEP = 0.05;

    public FreezingParticleEmitter(ClientWorld world, Vector3d pos) {
        this.world = world;
        this.position = pos;
        this.valid = true;
    }

    public FreezingParticleEmitter() {
        this.valid = false;
    }

    @Override
    public FreezingParticleEmitter instance(ClientWorld world, Vector3d pos) {
        return new FreezingParticleEmitter(world, pos);
    }

    @Override
    public void emitParticles() {
        if (!isValid()) return;

        for (int i = 0; i < 16; i++) {
            world.addParticle(ParticleTypes.ITEM_SNOWBALL, position.x, position.y+1, position.z, 0.0D, 0.0D, 0.0D);
        }
    }
}
