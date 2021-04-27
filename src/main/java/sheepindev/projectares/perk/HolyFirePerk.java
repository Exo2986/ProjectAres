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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import sheepindev.projectares.potion.HostilePerkEffect;
import sheepindev.projectares.registry.RegisterEffects;
import sheepindev.projectares.registry.RegisterPerks;

import java.util.List;
import java.util.Random;

import static sheepindev.projectares.util.RegistryHelper.prefix;

public class HolyFirePerk extends Perk {

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) {
        if (event.getEntity().world.isRemote()) return;

        if (event.getSource() != null && event.getSource().getTrueSource() != null
                && event.getSource().getTrueSource() instanceof PlayerEntity
                && event.getEntity() instanceof MonsterEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            MonsterEntity target = (MonsterEntity) event.getEntity();

            HostilePerkEffect effect = (HostilePerkEffect) RegisterEffects.HOLY_FIRE_EFFECT.get();
            effect.setSourceEntity(player);

            if (target.getActivePotionEffect(RegisterEffects.HOLY_FIRE_EFFECT.get()) == null &&
                    target.getActivePotionEffect(RegisterEffects.HOLY_FIRE_COOLDOWN_EFFECT.get()) == null
                    && !(target.world.isRaining() || target.world.isThundering())
                    && !target.isSwimming()) {
                System.out.println("holy fire");
                target.addPotionEffect(new EffectInstance(effect, 5 * 20, 0, false, false));
            }
        }
    }

    @Override
    public void onKill(ItemStack item, LivingDeathEvent event) {
        event.getEntityLiving().removePotionEffect(RegisterEffects.HOLY_FIRE_EFFECT.get());
    }

    @Override
    public void populateCompatible() {
        super.populateCompatible();
        compatiblePerks.remove(RegisterPerks.getRegisteredPerk(prefix("freezing")));
    }
}
