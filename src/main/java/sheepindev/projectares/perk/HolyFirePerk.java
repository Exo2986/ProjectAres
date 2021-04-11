package sheepindev.projectares.perk;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import sheepindev.projectares.registry.RegisterEffects;

import java.util.List;
import java.util.Random;

public class HolyFirePerk extends Perk {

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) {
        if (event.getEntity().world.isRemote()) return;

        if (event.getSource() != null && event.getSource().getTrueSource() != null
                && event.getSource().getTrueSource() instanceof PlayerEntity
                && event.getEntity() instanceof MonsterEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            MonsterEntity target = (MonsterEntity) event.getEntity();

            if (target.getActivePotionEffect(RegisterEffects.HOLY_FIRE_EFFECT.get()) == null &&
                    target.getActivePotionEffect(RegisterEffects.HOLY_FIRE_COOLDOWN_EFFECT.get()) == null)
                target.addPotionEffect(new EffectInstance(RegisterEffects.HOLY_FIRE_EFFECT.get(), 5 * 20, 0, false, false));
        }
    }
}
