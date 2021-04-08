package sheepindev.projectares.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;

import static sheepindev.projectares.ProjectAres.LOGGER;
import static sheepindev.projectares.enums.EvasionPerkEnum.*;
import static sheepindev.projectares.util.RegistryHelper.prefix;
import static sheepindev.projectares.util.PerkItemHelper.isPerkItemAndHasPerk;

public class EvasionPacketHandler {
    private static SortedMap<Integer, Long> cooldowns = new TreeMap<>();

    public static void onMessageReceived(final EvasionPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide side = ctx.getDirection().getReceptionSide();

        if (side != LogicalSide.SERVER) {
            LOGGER.warn("EvasionPacket received on wrong side: " + side);
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("Received invalid EvasionPacket " + message.toString());
            return;
        }

        final ServerPlayerEntity player = ctx.getSender();
        if (player == null) {
            LOGGER.warn("Received EvasionPacket from null ServerPlayerEntity");
            return;
        }

        ctx.enqueueWork(() -> processMessage(message, player));
    }

    private static void processMessage(EvasionPacket message, ServerPlayerEntity player) {
        if (player.world.isRemote()) return; //serverside only

        if (!isPerkItemAndHasPerk(player.getHeldItemMainhand(),prefix("evasion"))) return;

        if (!cooldowns.containsKey(player.getEntityId())) cooldowns.put(player.getEntityId(), player.world.getGameTime() - COOLDOWN);

        if (player.getEntityId() == message.getPlayer() && !(player instanceof FakePlayer)) {
            if ( ( player.world.getGameTime() - cooldowns.get(player.getEntityId()) ) >= COOLDOWN) {

                Vector3d directionVector;

                switch(message.getDirection()) {

                    case  LEFT:   directionVector = new Vector3d(EVADE_SPEED, 0, 0);
                        break;
                    case RIGHT:   directionVector = new Vector3d(-EVADE_SPEED, 0, 0);
                        break;
                    case  BACK:   directionVector = new Vector3d(0, 0, -EVADE_SPEED);
                        break;

                    default: return;
                }

                player.setMotion(directionVector.rotateYaw(-player.rotationYaw * ((float)Math.PI / 180F)));
                player.velocityChanged = true;
                cooldowns.put(player.getEntityId(), player.world.getGameTime());
            }
        }
    }
}
