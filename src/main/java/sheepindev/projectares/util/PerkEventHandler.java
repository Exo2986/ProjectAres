package sheepindev.projectares.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import sheepindev.projectares.item.PerkItem;

public class PerkEventHandler {
    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        Entity owner = event.getEntity();
        boolean perkItemFound = false;

        //We do it this way because we (theoretically) want this to work with non-player entities
        for (ItemStack itemStack : owner.getHeldEquipment()) {
            if (itemStack.getItem() instanceof PerkItem) {
                perkItemFound = true;

                PerkItem item = (PerkItem) itemStack.getItem();

                item.firePerkEvent(itemStack, (a) -> a.onOwnerDamage(itemStack, event));
            }
        }

        if (!perkItemFound) {
            Entity owner2 = event.getSource().getTrueSource();
            LivingEntity target = event.getEntityLiving();

            if (owner2 == null) return;

            for (ItemStack itemStack : owner2.getHeldEquipment()) {
                if (itemStack.getItem() instanceof PerkItem) {
                    PerkItem item = (PerkItem) itemStack.getItem();

                    if (target.getHealth() - event.getAmount() <= 0) {
                        item.firePerkEvent(itemStack, (a) -> a.onKill(itemStack, event));
                    } else {
                        item.firePerkEvent(itemStack, (a) -> a.onHit(itemStack, event));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        Entity owner = event.getEntity();

        for (ItemStack itemStack : owner.getHeldEquipment()) {
            if (itemStack.getItem() instanceof PerkItem) {

                PerkItem item = (PerkItem) itemStack.getItem();

                item.firePerkEvent(itemStack, (a) -> a.onOwnerDeath(itemStack, owner));
            }
        }
    }

    @SubscribeEvent
    public void onCriticalHit(CriticalHitEvent event) {
        if (!event.isVanillaCritical()) return;
        Entity owner = event.getEntity();

        for (ItemStack itemStack : owner.getHeldEquipment()) {
            if (itemStack.getItem() instanceof PerkItem) {

                PerkItem item = (PerkItem) itemStack.getItem();

                item.firePerkEvent(itemStack, (a) -> a.onCrit(itemStack, event));
            }
        }
    }
}
