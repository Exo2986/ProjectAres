package sheepindev.projectares.network;

import net.minecraft.network.PacketBuffer;

import static sheepindev.projectares.ProjectAres.LOGGER;

public class ExtendedReachPacket {
    private int playerID;
    private int targetID;
    private boolean valid = true;

    public ExtendedReachPacket(int playerID, int targetID) {
        this.playerID = playerID;
        this.targetID = targetID;
    }

    public int getPlayer() {
        return playerID;
    }

    public int getTarget() {
        return targetID;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isMessageValid() {
        return valid;
    }

    private ExtendedReachPacket() {
        valid = false;
    }

    public static ExtendedReachPacket decode(PacketBuffer buffer) {
        ExtendedReachPacket newPacket = new ExtendedReachPacket();
        try {
            newPacket.playerID = buffer.readInt();
            newPacket.targetID = buffer.readInt();
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
            LOGGER.warn("Exception while reading ExtendedReachPacket at server: " + e);
        }

        newPacket.valid = true;

        return newPacket;
    }

    public void encode (PacketBuffer buffer) {
        if (!isMessageValid()) return;
        buffer.writeInt(playerID);
        buffer.writeInt(targetID);
    }
}
