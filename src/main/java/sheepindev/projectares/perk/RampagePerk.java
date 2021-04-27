package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sheepindev.projectares.registry.RegisterEffects;

public class RampagePerk extends ApplyEffectPerk{

    @Override
    protected int getMaxAmplifier() {
        return 2;
    }

    @Override
    protected int getEffectDuration () {
        return 3*20;
    }

    protected void processKill(ItemStack item, LivingDeathEvent event) {
        if (event.getSource().getTrueSource() != null
                && event.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getSource().getTrueSource();

            applyOrAmplifyEffect(RegisterEffects.RAMPAGE_EFFECT.get(), entity);
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
