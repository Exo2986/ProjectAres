package sheepindev.projectares.perk;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import sheepindev.projectares.network.ExtendedReachPacket;
import sheepindev.projectares.network.ProjectAresPacketHandler;
import sheepindev.projectares.util.RayTracingHelper;

import static sheepindev.projectares.ProjectAres.LOGGER;

public class ReachPerk extends Perk{
    public static float EXTENDED_REACH_DISTANCE = 25;

    @Override
    public void onSwing(ItemStack item, Entity owner) {
        if (!owner.world.isRemote()) return;

        EntityRayTraceResult target = RayTracingHelper.entityRayTrace(owner, EXTENDED_REACH_DISTANCE);
        if (target != null) {
            double distance = owner.getPositionVec().distanceTo(target.getEntity().getPositionVec());
            LOGGER.debug(distance);

            RayTraceResult mouseOver = Minecraft.getInstance().objectMouseOver;

            if (mouseOver != null && mouseOver.getType() == RayTraceResult.Type.ENTITY && ((EntityRayTraceResult)mouseOver).getEntity().getEntityId() == target.getEntity().getEntityId()) return; //avoid attacking twice

            if (distance > EXTENDED_REACH_DISTANCE) return;

            ExtendedReachPacket packet = new ExtendedReachPacket(owner.getEntityId(), target.getEntity().getEntityId());
            ProjectAresPacketHandler.INSTANCE.sendToServer(packet);
        }
    }
}
