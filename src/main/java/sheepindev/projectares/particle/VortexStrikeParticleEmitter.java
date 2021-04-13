package sheepindev.projectares.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;


public class VortexStrikeParticleEmitter extends ParticleEmitter {
    private static final double RADIUS = 1;
    private static final int SLICES = 16;
    private static final double YSTEP = 0.05;

    public VortexStrikeParticleEmitter(ClientWorld world, Vector3d pos) {
        this.world = world;
        this.position = pos;
        this.valid = true;
    }

    public VortexStrikeParticleEmitter() {
        this.valid = false;
    }

    @Override
    public VortexStrikeParticleEmitter instance(ClientWorld world, Vector3d pos) {
        return new VortexStrikeParticleEmitter(world, pos);
    }

    @Override
    public void emitParticles() {
        if (!isValid()) return;
        double slice = 2 * Math.PI / SLICES;

        Vector3d prevPoint = new Vector3d(
                position.x + (RADIUS * Math.cos(-slice)),
                position.y+1,
                position.z + (RADIUS * Math.sin(-slice)));

        double y = position.y+0.75;

        for (int i = 0; i < SLICES; i++) {

            double angle = slice * i;

            Vector3d pointOnCircle = new Vector3d(
                    position.x + (RADIUS * Math.cos(angle)),
                    y,
                    position.z + (RADIUS * Math.sin(angle)));

            world.addParticle(ParticleTypes.CLOUD, pointOnCircle.x, pointOnCircle.y, pointOnCircle.z, (pointOnCircle.x - prevPoint.x)/2, YSTEP, (pointOnCircle.z - prevPoint.z)/2);

            prevPoint = pointOnCircle;
            y+=YSTEP;
        }
    }
}
