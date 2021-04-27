package sheepindev.projectares.registry;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sheepindev.projectares.ProjectAres;
import sheepindev.projectares.potion.BasicEffect;
import sheepindev.projectares.potion.FreezingEffect;
import sheepindev.projectares.potion.HolyFireEffect;
import sheepindev.projectares.potion.RampageEffect;

import java.util.UUID;

import static sheepindev.projectares.util.ProjectAresConstants.MOD_ID;

public class RegisterEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, MOD_ID);

    public static final RegistryObject<Effect> RAMPAGE_EFFECT = EFFECTS.register("rampage", () ->
            new RampageEffect(EffectType.BENEFICIAL, 9643043, 1.1D).addAttributesModifier(Attributes.ATTACK_DAMAGE, String.valueOf(UUID.randomUUID()), 0.0D, AttributeModifier.Operation.ADDITION));

    public static final RegistryObject<Effect> HOLY_FIRE_EFFECT = EFFECTS.register("holy_fire", () ->
            new HolyFireEffect(EffectType.HARMFUL, 9643043));

    public static final RegistryObject<Effect> FREEZING_EFFECT = EFFECTS.register("freezing", () ->
            new FreezingEffect(EffectType.HARMFUL, 9643043));

    public static final RegistryObject<Effect> HOLY_FIRE_COOLDOWN_EFFECT = EFFECTS.register("holy_fire_cooldown", () ->
            new BasicEffect(EffectType.NEUTRAL, 9643043));

    public static final RegistryObject<Effect> THRILL_EFFECT = EFFECTS.register("thrill_of_the_kill", () ->
            new BasicEffect(EffectType.BENEFICIAL, 9643043).addAttributesModifier(Attributes.ATTACK_SPEED, String.valueOf(UUID.randomUUID()), 0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> ADRENALINE_RUSH_EFFECT = EFFECTS.register("adrenaline_rush", () ->
            new BasicEffect(EffectType.BENEFICIAL, 9643043).addAttributesModifier(Attributes.ATTACK_SPEED, String.valueOf(UUID.randomUUID()), 0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> KILLING_WIND_EFFECT = EFFECTS.register("killing_wind", () ->
            new BasicEffect(EffectType.BENEFICIAL, 9643043).addAttributesModifier(Attributes.MOVEMENT_SPEED, String.valueOf(UUID.randomUUID()), 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public static final RegistryObject<Effect> MOVING_TARGET_EFFECT = EFFECTS.register("moving_target", () ->
            new BasicEffect(EffectType.BENEFICIAL, 9643043).addAttributesModifier(Attributes.ATTACK_SPEED, String.valueOf(UUID.randomUUID()), 0.5F, AttributeModifier.Operation.MULTIPLY_BASE));
}
