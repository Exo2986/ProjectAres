package sheepindev.projectares.perk;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sheepindev.projectares.registry.RegisterEffects;
import sheepindev.projectares.registry.RegisterPerks;

import java.util.ArrayList;
import java.util.Arrays;

public class AdrenalineRushPerk extends Perk{

    @Override
    public void onOwnerDamage(ItemStack item, LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();

        entity.removePotionEffect(RegisterEffects.ADRENALINE_RUSH_EFFECT.get());

        entity.addPotionEffect(new EffectInstance(RegisterEffects.ADRENALINE_RUSH_EFFECT.get(), 2 * 20, 0, false, false));
    }
}
