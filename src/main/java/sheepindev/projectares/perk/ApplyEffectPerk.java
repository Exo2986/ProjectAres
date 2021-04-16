package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

public class ApplyEffectPerk extends Perk {
    protected int getMaxAmplifier() {
        return -1;
    }

    protected int getEffectDuration () {
        return 0;
    }

    public void applyOrAmplifyEffect(Effect effect, LivingEntity target) {

        EffectInstance activeEffect = target.getActivePotionEffect(effect);

        int amplifier = activeEffect == null ? 0 : activeEffect.getAmplifier()+1;
        target.removePotionEffect(effect);

        int maxAmp = getMaxAmplifier();
        if (maxAmp != -1)
            amplifier = Math.min(amplifier, maxAmp);

        target.addPotionEffect(new EffectInstance(effect, getEffectDuration(), amplifier, false, false));
    }
}