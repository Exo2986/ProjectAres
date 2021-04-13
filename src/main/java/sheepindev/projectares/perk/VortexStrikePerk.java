package sheepindev.projectares.perk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.PacketDistributor;
import sheepindev.projectares.network.ParticleEmitterPacket;
import sheepindev.projectares.network.ProjectAresPacketHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import static sheepindev.projectares.util.RegistryHelper.prefix;

public class VortexStrikePerk extends Perk {

    @Override
    public void onCrit(ItemStack item, CriticalHitEvent event) {
        if (event.getEntity().world.isRemote()) return;

        PlayerEntity player = event.getPlayer();
        Entity target = event.getTarget();

        AxisAlignedBB aabb = target.getBoundingBox().grow(2);

        List<Entity> entities = player.world.getEntitiesInAABBexcluding(target, aabb,
                (e) -> e instanceof LivingEntity &&
                        e.getEntityId() != player.getEntityId());

        player.world.playSound(null, target.getPosX(), target.getPosY(), target.getPosZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.PLAYERS, 0.5f, 0.8f);

        float damage = 0;
        Collection<AttributeModifier> modifiers = item.getAttributeModifiers(EquipmentSlotType.MAINHAND).get(Attributes.ATTACK_DAMAGE);
        for (AttributeModifier modifier : modifiers) {
            damage+=modifier.getAmount();
        }

        for (Entity entity : entities) {
            entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
        }
        ProjectAresPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> target),
            new ParticleEmitterPacket(target.getPositionVec(), prefix("vortex_strike_particle_emitter").toString()));
    }
}
