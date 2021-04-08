package sheepindev.projectares.network;

import net.minecraft.network.PacketBuffer;

import static sheepindev.projectares.ProjectAres.LOGGER;

public class EvasionPacket {
    private int playerID;
    private int direction;
    private boolean valid = true;

    public EvasionPacket(int playerID, int direction) {
        this.playerID = playerID;
        this.direction = direction;
    }

    public int getPlayer() {
        return playerID;
    }

    public int getDirection() {
        return direction;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isMessageValid() {
        return valid;
    }

    private EvasionPacket() {
        valid = false;
    }

    public static EvasionPacket decode(PacketBuffer buffer) {
        EvasionPacket newPacket = new EvasionPacket();
        try {
            newPacket.playerID = buffer.readInt();
            newPacket.direction = buffer.readInt();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading EvasionPacket at server: " + e);
        }

        newPacket.valid = true;

        return newPacket;
    }

    public void encode (PacketBuffer buffer) {
        if (!isMessageValid()) return;
        buffer.writeInt(playerID);
        buffer.writeInt(direction);
    }
}
