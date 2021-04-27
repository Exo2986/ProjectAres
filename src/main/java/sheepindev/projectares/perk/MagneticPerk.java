package sheepindev.projectares.perk;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.Random;

public class MagneticPerk extends Perk {

    @Override
    public void onTick(ItemStack item, Entity owner) {
        if (owner.world.isRemote()) return;

        AxisAlignedBB aabb = owner.getBoundingBox().grow(8);

        List<Entity> entities = owner.world.getEntitiesInAABBexcluding(owner, aabb,
                (e) -> e instanceof MonsterEntity || e instanceof ItemEntity || e instanceof ExperienceOrbEntity);

        for (Entity entity : entities) {
            Vector3d motion = owner.getPositionVec().normalize().subtract(entity.getPositionVec().normalize()).mul(2.4, 0, 2.4);

            entity.addVelocity(motion.x, 0, motion.z);
            entity.velocityChanged = true;
        }
    }
}
