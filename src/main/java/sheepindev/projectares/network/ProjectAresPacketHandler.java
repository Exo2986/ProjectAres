package sheepindev.projectares.network;

import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

import static sheepindev.projectares.util.RegistryHelper.prefix;

public class ProjectAresPacketHandler {
    public static final byte EXTENDED_REACH_ID = 1;
    public static final byte EVASION_ID = 2;
    public static final byte BASIC_PARTICLE_EMITTER_ID = 3;

    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;

    public static void setupPackets() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                prefix("main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );
        INSTANCE.registerMessage(EXTENDED_REACH_ID, ExtendedReachPacket.class,
                ExtendedReachPacket::encode, ExtendedReachPacket::decode,
                ExtendedReachPacketHandler::onMessageReceived,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        INSTANCE.registerMessage(EVASION_ID, EvasionPacket.class,
                EvasionPacket::encode, EvasionPacket::decode,
                EvasionPacketHandler::onMessageReceived,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));

        INSTANCE.registerMessage(BASIC_PARTICLE_EMITTER_ID, ParticleEmitterPacket.class,
                ParticleEmitterPacket::encode, ParticleEmitterPacket::decode,
                ParticleEmitterPacketHandler::onMessageReceived,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }
}
