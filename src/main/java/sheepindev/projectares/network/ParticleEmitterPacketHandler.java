package sheepindev.projectares.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import sheepindev.projectares.particle.ParticleEmitter;
import sheepindev.projectares.registry.RegisterParticleEmitters;

import java.util.function.Supplier;

import static sheepindev.projectares.ProjectAres.LOGGER;

public class ParticleEmitterPacketHandler {
    public static void onMessageReceived(final ParticleEmitterPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide side = ctx.getDirection().getReceptionSide();

        if (side != LogicalSide.CLIENT) {
            LOGGER.warn("ParticleEmitterPacket received on wrong side: " + side);
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("Received invalid ParticleEmitterPacket " + message.toString());
            return;
        }

        ctx.enqueueWork(() -> processMessage(message));

        ctx.setPacketHandled(true);
    }

    private static void processMessage(ParticleEmitterPacket message) {
        //new VortexStrikeParticleEmitter(Minecraft.getInstance().world, message.getPosition());
        ParticleEmitter emitter = RegisterParticleEmitters.getRegisteredEmitter(message.getRegistryID());
        if (emitter != null) {
            emitter = emitter.instance(Minecraft.getInstance().world, message.getPosition());
            emitter.emitParticles();
        }
    }
}
