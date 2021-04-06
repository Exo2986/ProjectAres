package sheepindev.projectares.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import sheepindev.projectares.ProjectAres;

import java.util.Optional;

public class ProjectAresPacketHandler {
    public static final byte EXTENDED_REACH_ID = 25;

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ProjectAres.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void SetupPackets() {
        INSTANCE.registerMessage(EXTENDED_REACH_ID, ExtendedReachPacket.class,
                ExtendedReachPacket::encode, ExtendedReachPacket::decode,
                ExtendedReachPacketHandler::onMessageReceived,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
