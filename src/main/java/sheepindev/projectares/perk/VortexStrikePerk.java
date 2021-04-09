package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import java.util.List;

public class VortexStrikePerk extends Perk {

    @Override
    public void onCrit(ItemStack item, CriticalHitEvent event) {
        if (event.getEntity().world.isRemote()) return;

        PlayerEntity player = event.getPlayer();

        AxisAlignedBB aabb = event.getTarget().getBoundingBox().grow(1);

        List<Entity> entities = player.world.getEntitiesInAABBexcluding(event.getTarget(), aabb,
                (e) -> e.isLiving() &&
                        e.getEntityId() != player.getEntityId());

        for (Entity entity : entities) {
            player.attackTargetEntityWithCurrentItem(entity);
        }
    }
}
