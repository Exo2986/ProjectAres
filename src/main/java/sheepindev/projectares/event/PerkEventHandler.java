package sheepindev.projectares.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import sheepindev.projectares.item.PerkItem;

import static sheepindev.projectares.util.RegistryHelper.prefix;

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

                    item.firePerkEvent(itemStack, (a) -> a.onHit(itemStack, event));
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
                return;
            }
        }

        if (event.getSource().getTrueSource() != null
                && event.getSource().getTrueSource() instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) event.getSource().getTrueSource();

            for (ItemStack itemStack : entity.getHeldEquipment()) {
                if (itemStack.getItem() instanceof PerkItem) {

                    PerkItem item = (PerkItem) itemStack.getItem();

                    item.firePerkEvent(itemStack, (a) -> a.onKill(itemStack, event));
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public void onCriticalHit(CriticalHitEvent event) {
        Entity owner = event.getEntity();

        for (ItemStack itemStack : owner.getHeldEquipment()) {
            if (itemStack.getItem() instanceof PerkItem) {

                PerkItem item = (PerkItem) itemStack.getItem();

                item.firePerkEvent(itemStack, (a) -> a.onCrit(itemStack, event));
            }
        }
    }
}
