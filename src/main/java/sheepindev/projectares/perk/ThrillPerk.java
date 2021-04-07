package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sheepindev.projectares.registry.RegisterEffects;

public class ThrillPerk extends Perk{

    @Override
    public void onKill(ItemStack item, LivingDeathEvent event) {
        if (event.getSource().getTrueSource() != null
                && event.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getSource().getTrueSource();

            EffectInstance currentThrill = entity.getActivePotionEffect(RegisterEffects.THRILL_EFFECT.get());
            int amplifier = currentThrill == null ? 0 : currentThrill.getAmplifier() + 1; //Increase amplifier if effect is already present
            amplifier = amplifier > 2 ? amplifier = 2 : amplifier; //Do not allow the effect to exceed 3, amplification indexes from 0

            entity.removePotionEffect(RegisterEffects.THRILL_EFFECT.get());

            entity.addPotionEffect(new EffectInstance(RegisterEffects.THRILL_EFFECT.get(), 5 * 20, amplifier, false, false));
        }
    }
}
