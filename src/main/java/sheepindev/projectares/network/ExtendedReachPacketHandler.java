package sheepindev.projectares.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import sheepindev.projectares.item.PerkItem;
import sheepindev.projectares.perk.ReachPerk;

import java.util.function.Supplier;

import static sheepindev.projectares.ProjectAres.LOGGER;
import static sheepindev.projectares.util.RegistryHelper.prefix;

public class ExtendedReachPacketHandler {
    public static void onMessageReceived(final ExtendedReachPacket message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        LogicalSide side = ctx.getDirection().getReceptionSide();

        if (side != LogicalSide.SERVER) {
            LOGGER.warn("ExtendedReachPacket received on wrong side: " + side);
            return;
        }
        if (!message.isMessageValid()) {
            LOGGER.warn("Received invalid ExtendedReachPacket " + message.toString());
            return;
        }

        final ServerPlayerEntity player = ctx.getSender();
        if (player == null) {
            LOGGER.warn("Received ExtendedReachPacket from null ServerPlayerEntity");
            return;
        }

        ctx.enqueueWork(() -> processMessage(message, player));
    }

    private static void processMessage(ExtendedReachPacket message, ServerPlayerEntity player) {
        Entity entity = player.world.getEntityByID(message.getTarget());

        if (entity instanceof LivingEntity) {

            LivingEntity target = (LivingEntity) entity;
            float distance = (float) player.getPositionVec().squareDistanceTo(target.getPositionVec());

            LOGGER.debug(distance);

            if (distance <= ReachPerk.EXTENDED_REACH_DISTANCE && player.getHeldItemMainhand().getItem() instanceof PerkItem) {

                PerkItem item = (PerkItem) player.getHeldItemMainhand().getItem();

                if (item.hasPerk(player.getHeldItemMainhand(), prefix("extended_blade").toString())) {
                    LOGGER.debug("ATTACKING!!!!");
                    player.attackTargetEntityWithCurrentItem(target);
                } else {
                    LOGGER.warn("Received ExtendedReachPacket with invalid PerkItem");
                }
            } else {
                LOGGER.warn("Received ExtendedReachPacket with invalid entity distance");
            }

        }
    }
}
