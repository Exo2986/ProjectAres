package sheepindev.projectares.potion;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import sheepindev.projectares.registry.RegisterEffects;

import javax.annotation.Nonnull;
import java.util.List;

import static sheepindev.projectares.util.RegistryHelper.prefix;

public class HolyFireEffect extends HostilePerkEffect {

    public HolyFireEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public ResourceLocation getSourcePerk() {
        return prefix("holy_fire");
    }

    @Override
    public void performEffect (@Nonnull LivingEntity target, int amplifier) {
        target.setFire(1);
        AxisAlignedBB aabb = target.getBoundingBox().grow(3);

        List<Entity> entities = target.world.getEntitiesInAABBexcluding(target, aabb,
                (e) -> e instanceof MonsterEntity);

        this.checkIndirectDeathEvent(target, 0);

        for (Entity entity : entities) {
            MonsterEntity monster = (MonsterEntity) entity;

            if (monster.getActivePotionEffect(RegisterEffects.HOLY_FIRE_EFFECT.get()) == null &&
                    monster.getActivePotionEffect(RegisterEffects.HOLY_FIRE_COOLDOWN_EFFECT.get()) == null
                    && !(monster.world.isRaining() || monster.world.isThundering())
                    && !monster.isSwimming())
                monster.addPotionEffect(new EffectInstance(RegisterEffects.HOLY_FIRE_EFFECT.get(), 5 * 20, amplifier, false, false));

        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return duration % 19 == 0; //every second-ish
    }

    @Override
    public void removeAttributesModifiersFromEntity(LivingEntity entity, AttributeModifierManager attributeMapIn, int amplifier) {
        entity.addPotionEffect(new EffectInstance(RegisterEffects.HOLY_FIRE_COOLDOWN_EFFECT.get(), 6*20, 0, false, false));
    }
}
