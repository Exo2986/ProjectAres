package sheepindev.projectares.perk;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;

import java.util.List;
import java.util.Random;

public class CopperCoatingPerk extends Perk {

    @Override
    public void onCrit(ItemStack item, CriticalHitEvent event) {
        if (event.getEntity().world.isRemote()) return;

        if (event.getPlayer() != null) {
            PlayerEntity player = event.getPlayer();
            Entity target = event.getTarget();

            if (target.world instanceof ServerWorld
                    && ( target.world.isRaining() || target.world.isThundering() ) ) {
                BlockPos blockpos = target.getPosition();

                if (target.world.canSeeSky(blockpos)) {
                    LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(target.world);
                    lightningboltentity.moveForced(Vector3d.copyCenteredHorizontally(blockpos));

                    lightningboltentity.setCaster((ServerPlayerEntity) player);
                    player.world.addEntity(lightningboltentity);
                }
            }
        }
    }
}
