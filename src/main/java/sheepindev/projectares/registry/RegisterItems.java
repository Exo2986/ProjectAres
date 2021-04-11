package sheepindev.projectares.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import sheepindev.projectares.ProjectAres;
import sheepindev.projectares.item.PerkItem;

public class RegisterItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ProjectAres.MOD_ID);

    public static final RegistryObject<PerkItem> PERK_ITEM_TEST = ITEMS.register("perk_item_test", () -> new PerkItem(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)));
    public static final RegistryObject<PerkItem> DUNGEON_SWORD = ITEMS.register("dungeon_sword", () -> new PerkItem(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1)));
}
