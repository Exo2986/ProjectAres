package sheepindev.projectares.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RayTracingHelper {
    public static AxisAlignedBB getViewAxisAlignedBB() { //this might be unnecessary
        Entity renderViewEntity = Minecraft.getInstance().renderViewEntity;

        return renderViewEntity == null ? null
                : new AxisAlignedBB(
                renderViewEntity.getPosX()-0.5D,
                renderViewEntity.getPosY()-0.0D,
                renderViewEntity.getPosZ()-0.5D,
                renderViewEntity.getPosX()+0.5D,
                renderViewEntity.getPosY()+1.5D,
                renderViewEntity.getPosZ()+0.5D
        );
    }

    //This is pretty much entirely copied from GameRenderer#getMouseOver but modified for my uses
    //Remember kids, keep your code readable and all will be good in the world
    public static EntityRayTraceResult entityRayTrace(Entity sourceEntity, float distance) {
        Minecraft mc = Minecraft.getInstance();

        if (sourceEntity != null && mc.world != null) {
            float partialTicks = mc.getRenderPartialTicks();

            Vector3d lookVector = sourceEntity.getLook(1.0f);
            Vector3d eyePosition = sourceEntity.getEyePosition(partialTicks);

            Vector3d endPosition = eyePosition.add(lookVector.x * distance, lookVector.y * distance, lookVector.z * distance);
            AxisAlignedBB rayCollision = sourceEntity.getBoundingBox().expand(lookVector.scale(distance)).grow(1.0D);

            EntityRayTraceResult result = ProjectileHelper.rayTraceEntities(sourceEntity, eyePosition, endPosition, rayCollision,
                    (entity) -> !entity.isSpectator() && entity.canBeCollidedWith(),
                    distance);

            if (result != null) {
                Entity foundEntity = result.getEntity();
                Vector3d hitVector = result.getHitVec();
                double foundDistance = eyePosition.squareDistanceTo(hitVector);
                if (foundDistance > distance) {
                    return null;
                } else {
                    return result;
                }
            }
        }
        return null;
    }
}
