package sheepindev.projectares.event.loot;

import net.minecraft.item.Item;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ObjectHolder;
import sheepindev.projectares.ProjectAres;

import static sheepindev.projectares.util.ProjectAresConstants.MOD_ID;

public class LootTableEventHandler {
    public static int dungeonSwordWeight = 7;

    @ObjectHolder(MOD_ID + ":dungeon_sword")
    public static final Item dungeonSword = null;

    public static LootFunctionType SET_PERKS;

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        SET_PERKS = new LootFunctionType(new SetPerks.Serializer());

        System.out.println("ADDING LOOT");

        if (dungeonSwordWeight > 0 && event.getName().equals(LootTables.CHESTS_SIMPLE_DUNGEON)) {
            event.getTable().addPool(LootPool.builder().addEntry(
                    ItemLootEntry.builder(dungeonSword)
                            .weight(dungeonSwordWeight)
                            .quality(2)
                            .acceptFunction(() -> new SetPerks(new ILootCondition[0])))

                    .build());
        }
    }
}
