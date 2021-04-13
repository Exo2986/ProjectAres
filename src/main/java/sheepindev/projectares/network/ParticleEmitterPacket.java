package sheepindev.projectares.network;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;

import static sheepindev.projectares.ProjectAres.LOGGER;

public class ParticleEmitterPacket {
    private Vector3d position;
    private String registryID;
    private boolean valid = true;

    public ParticleEmitterPacket(Vector3d position, String registryID) {
        this.position = position;
        this.registryID = registryID;
    }

    public Vector3d getPosition() { return position; }

    public String getRegistryID() { return registryID; }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isMessageValid() {
        return valid;
    }

    private ParticleEmitterPacket() {
        valid = false;
    }

    public static ParticleEmitterPacket decode(PacketBuffer buffer) {
        ParticleEmitterPacket newPacket = new ParticleEmitterPacket();
        try {
            newPacket.position = new Vector3d(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
            newPacket.registryID = buffer.readString();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading ParticleEmitterPacket at client: " + e);
        }

        newPacket.valid = true;

        return newPacket;
    }

    public void encode (PacketBuffer buffer) {
        if (!isMessageValid()) return;
        buffer.writeDouble(position.x);
        buffer.writeDouble(position.y);
        buffer.writeDouble(position.z);
        buffer.writeString(registryID);
    }
}
