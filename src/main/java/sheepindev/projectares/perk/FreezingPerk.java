package sheepindev.projectares.perk;

import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import sheepindev.projectares.registry.RegisterEffects;

public class FreezingPerk extends Perk {

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) {
        if (event.getEntity().world.isRemote()) return;

        if (event.getSource() != null && event.getSource().getTrueSource() != null
                && event.getSource().getTrueSource() instanceof PlayerEntity
                && event.getEntity() instanceof MonsterEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            MonsterEntity target = (MonsterEntity) event.getEntity();

            int amplifier = 0;

            EffectInstance activeEffect = target.getActivePotionEffect(RegisterEffects.FREEZING_EFFECT.get());
            if (activeEffect != null) {
                amplifier = activeEffect.getAmplifier()+1;
                target.removePotionEffect(activeEffect.getPotion());
            }

            target.addPotionEffect(new EffectInstance(RegisterEffects.FREEZING_EFFECT.get(), 5 * 20, amplifier, false, false));
        }
    }
}
