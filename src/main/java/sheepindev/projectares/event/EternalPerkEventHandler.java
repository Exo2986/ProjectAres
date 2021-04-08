package sheepindev.projectares.event;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import sheepindev.projectares.item.PerkItem;

import java.util.Iterator;

import static sheepindev.projectares.util.RegistryHelper.prefix;
import static sheepindev.projectares.util.PerkItemHelper.isPerkItemAndHasPerk;

public class EternalPerkEventHandler {

    @SubscribeEvent
    public void playerItemDropEvent(LivingDropsEvent event) {
        if (event.getEntity().world.isRemote()) return;
        if (event.getEntity() instanceof PlayerEntity
                && !(event.getEntity() instanceof FakePlayer)
                && !event.getEntity().world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {

            PlayerEntity player = (PlayerEntity) event.getEntity();

            Iterator<ItemEntity> iterator = event.getDrops().iterator();
            while(iterator.hasNext()) {

                ItemStack stack = iterator.next().getItem();

                if (isPerkItemAndHasPerk(stack, prefix("eternal"))) {
                    PerkItem item = (PerkItem) stack.getItem();

                    if (player.addItemStackToInventory(stack.copy())) {
                        iterator.remove();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void playerCloneEvent(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return; //what the hell is that function name

        PlayerEntity newPlayer = event.getPlayer();
        PlayerEntity oldPlayer = event.getOriginal();

        if (newPlayer instanceof FakePlayer || newPlayer.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

        for (int slot = 0; slot < oldPlayer.inventory.mainInventory.size(); slot++) {

            ItemStack stack = oldPlayer.inventory.mainInventory.get(slot);

            if (isPerkItemAndHasPerk(stack,prefix("eternal"))) {

                PerkItem item = (PerkItem) stack.getItem();

                newPlayer.addItemStackToInventory(stack);
                oldPlayer.inventory.mainInventory.set(slot, ItemStack.EMPTY);

            }

        }
    }

}
