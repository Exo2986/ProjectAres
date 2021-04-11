package sheepindev.projectares.perk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.DistExecutor;

import java.util.List;

public class VortexStrikePerk extends Perk {

    @Override
    public void onCrit(ItemStack item, CriticalHitEvent event) {
        if (event.getEntity().world.isRemote()) { //client
            Entity target = event.getTarget();
            new DoParticles((ClientWorld) target.world, target.getPositionVec());
        } else { //server
            PlayerEntity player = event.getPlayer();
            Entity target = event.getTarget();

            AxisAlignedBB aabb = target.getBoundingBox().grow(1);

            List<Entity> entities = player.world.getEntitiesInAABBexcluding(target, aabb,
                    (e) -> e instanceof LivingEntity &&
                            e.getEntityId() != player.getEntityId());

            player.world.playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.PLAYERS, 0.5f, 0.8f);

            for (Entity entity : entities) {
                player.attackTargetEntityWithCurrentItem(entity);
            }
        }
    }

    private class DoParticles {
        private static final double RADIUS = 1;
        private static final int SLICES = 16;
        private static final double YSTEP = 0.05;

        protected DoParticles(ClientWorld world, Vector3d pos) {
            emitParticles(pos, world);
        }

        @OnlyIn(Dist.CLIENT)
        private void emitParticles(Vector3d position, World world) {
            double slice = 2 * Math.PI / 16;

            Vector3d prevPoint = new Vector3d(
                    position.x + (RADIUS * Math.cos(-slice)),
                    position.y+1,
                    position.z + (RADIUS * Math.sin(-slice)));

            double y = position.y+0.75;

            for (int i = 0; i < SLICES; i++) {

                double angle = slice * i;

                Vector3d pointOnCircle = new Vector3d(
                        position.x + (RADIUS * Math.cos(angle)),
                        y,
                        position.z + (RADIUS * Math.sin(angle)));

                world.addParticle(ParticleTypes.CLOUD, pointOnCircle.x, pointOnCircle.y, pointOnCircle.z, (pointOnCircle.x - prevPoint.x)/2, YSTEP, (pointOnCircle.z - prevPoint.z)/2);

                prevPoint = pointOnCircle;
                y+=YSTEP;
            }
        }
    }
}
