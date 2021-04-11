package sheepindev.projectares.perk;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.CreatureEntity;
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

import java.util.List;
import java.util.Random;

public class ConfusionPerk extends Perk {

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) {
        if (event.getEntity().world.isRemote()) return;

        if (event.getSource() != null && event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            Entity target = event.getEntity();

            AxisAlignedBB aabb = target.getBoundingBox().grow(3);

            List<Entity> entities = player.world.getEntitiesInAABBexcluding(target, aabb,
                    (e) -> e instanceof CreatureEntity &&
                            e.getEntityId() != player.getEntityId());

            Random random = new Random();

            for (Entity entity : entities) {
                if (random.nextInt(10) == 1) {
                    ((CreatureEntity) entity).setAttackTarget((LivingEntity) entities.get(random.nextInt(entities.size())));
                }
            }
        }
    }
}
