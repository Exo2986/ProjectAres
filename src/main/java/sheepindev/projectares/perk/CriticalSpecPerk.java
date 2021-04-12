package sheepindev.projectares.perk;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;

public class CriticalSpecPerk extends Perk {

    private final HashMap<Integer, Long> playerCooldowns = new HashMap<>();

    @Override
    public void onHit(ItemStack item, LivingDamageEvent event) {
        if (event.getEntity().world.isRemote()) return;
        if (event.getSource().getTrueSource() != null && event.getSource().getTrueSource() instanceof PlayerEntity) {
            PlayerEntity owner = (PlayerEntity) event.getSource().getTrueSource();

            LivingEntity target = event.getEntityLiving();

            if (playerCooldowns.containsKey(owner.getEntityId())
                    && playerCooldowns.get(owner.getEntityId()) == owner.world.getGameTime()) return; //prevent double crit

            event.setAmount(event.getAmount() * 1.15f);

            owner.world.playSound(null, owner.getPosX(), owner.getPosY(), owner.getPosZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, owner.getSoundCategory(), 1.0F, 1.0F);
            owner.onCriticalHit(target);

            CriticalHitEvent hitResult = new CriticalHitEvent(owner, target, 1.15f, false);
            MinecraftForge.EVENT_BUS.post(hitResult);
        }
    }

    @Override
    public void onCrit(ItemStack item, CriticalHitEvent event) {
        if (event.getPlayer() != null) {
            playerCooldowns.put(event.getPlayer().getEntityId(), event.getPlayer().world.getGameTime());
        }
    }
}
