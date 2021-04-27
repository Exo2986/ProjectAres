package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sheepindev.projectares.registry.RegisterEffects;

public class KillingWindPerk extends ApplyEffectPerk {
    @Override
    protected int getMaxAmplifier() {
        return 4;
    }

    @Override
    protected int getEffectDuration () {
        return 5*20;
    }

    public void processKill(ItemStack item, LivingDeathEvent event) {
        if (event.getSource().getTrueSource() != null
                && event.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getSource().getTrueSource();

            applyOrAmplifyEffect(RegisterEffects.KILLING_WIND_EFFECT.get(), entity);
        }
    }

    @Override
    public void onKill(ItemStack item, LivingDeathEvent event) {
        processKill(item, event);
    }

    @Override
    public void onIndirectKill(ItemStack item, LivingDeathEvent event) {
        processKill(item, event);
    }
}
