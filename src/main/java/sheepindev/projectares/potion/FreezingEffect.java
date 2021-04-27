package sheepindev.projectares.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.network.PacketDistributor;
import sheepindev.projectares.network.ParticleEmitterPacket;
import sheepindev.projectares.network.ProjectAresPacketHandler;
import sheepindev.projectares.registry.RegisterEffects;

import javax.annotation.Nonnull;
import java.util.List;

import static sheepindev.projectares.util.RegistryHelper.prefix;

public class FreezingEffect extends HostilePerkEffect {

    public FreezingEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public ResourceLocation getSourcePerk() {
        return prefix("freezing");
    }

    @Override
    public void performEffect (@Nonnull LivingEntity target, int amplifier) {
        if (target instanceof StrayEntity) {
            target.removePotionEffect(this);
            return;
        }

        float damage = 2*amplifier;

        this.checkIndirectDeathEvent(target, damage);

        if (amplifier > 0)
            target.attackEntityFrom(DamageSource.MAGIC, 2*amplifier);

        if (!target.world.isRemote()) {
            ProjectAresPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> target),
                    new ParticleEmitterPacket(target.getPositionVec(), prefix("freezing_particle_emitter").toString()));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}
