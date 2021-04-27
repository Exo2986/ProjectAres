package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import sheepindev.projectares.potion.HostilePerkEffect;
import sheepindev.projectares.registry.RegisterEffects;
import sheepindev.projectares.registry.RegisterPerks;

import java.util.Arrays;

import static sheepindev.projectares.util.RegistryHelper.prefix;

public class FreezingPerk extends ApplyEffectPerk {
    @Override
    protected int getEffectDuration () {
        return 5*20;
    }

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) {
        if (event.getEntity().world.isRemote()) return;

        if (event.getSource() != null && event.getSource().getTrueSource() != null
                && event.getSource().getTrueSource() instanceof PlayerEntity
                && event.getEntity() instanceof MonsterEntity) {
            MonsterEntity target = (MonsterEntity) event.getEntity();

            HostilePerkEffect effect = (HostilePerkEffect) RegisterEffects.FREEZING_EFFECT.get();
            effect.setSourceEntity((LivingEntity) event.getSource().getTrueSource());

            applyOrAmplifyEffect(effect, target);
        }
    }

    @Override
    public void populateCompatible() {
        super.populateCompatible();
        compatiblePerks.remove(RegisterPerks.getRegisteredPerk(prefix("holy_fire")));
    }
}
