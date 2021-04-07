package sheepindev.projectares.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import sheepindev.projectares.command.arguments.PerkArgument;
import sheepindev.projectares.item.PerkItem;
import sheepindev.projectares.perk.Perk;
import sheepindev.projectares.registry.RegisterPerks;

import static sheepindev.projectares.util.RegistryHelper.prefix;

public class PerkItemCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> command =
                Commands.literal("perkitem")
                .requires((commandSource -> commandSource.hasPermissionLevel(2))).executes((context ->
                        giveItem(context.getSource(), null, null)))
                .then(Commands.argument("perk1", PerkArgument.perk()).executes((context ->
                        giveItem(context.getSource(), PerkArgument.getPerk(context,"perk1"), null)))
                .then(Commands.argument("perk2",PerkArgument.perk()).executes((context ->
                        giveItem(context.getSource(), PerkArgument.getPerk(context,"perk1"), PerkArgument.getPerk(context, "perk2"))))));

        dispatcher.register(command);
    }

    private static int giveItem(CommandSource source, Perk perk1, Perk perk2) throws CommandSyntaxException {
        PerkItem item = (PerkItem) ForgeRegistries.ITEMS.getValue(prefix("perk_item_test"));
        if (item == null) {
            return 0;
        }
        ItemStack stack = new ItemStack(item);

        if (perk1 == null)
            item.randomizePerk(stack);
        else
            item.addPerk(stack, perk1);

        if (perk2 == null)
            item.randomizePerk(stack);
        else
            item.addPerk(stack, perk2);

        source.asPlayer().addItemStackToInventory(stack);
        return 1;
    }
}
