package sheepindev.projectares.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ParticleEmitter extends ForgeRegistryEntry<ParticleEmitter> {
    protected boolean valid = false;

    protected ClientWorld world;
    protected Vector3d position;

    public void emitParticles() {

    }

    public boolean isValid() {
        return valid;
    }

    public ParticleEmitter instance(ClientWorld world, Vector3d pos) {
        return null;
    }
}
