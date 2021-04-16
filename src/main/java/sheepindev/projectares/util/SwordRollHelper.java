package sheepindev.projectares.util;

import net.minecraft.item.ItemStack;
import sheepindev.projectares.item.PerkItem;
import sheepindev.projectares.perk.Perk;
import sheepindev.projectares.registry.RegisterPerks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SwordRollHelper {
    protected static Random random = new Random();

    public static ItemStack getRolledItem(PerkItem item) {
        Perk[] perks = RegisterPerks.getRegisteredPerks();
        ItemStack itemStack = new ItemStack(item);

        Perk first = perks[random.nextInt(perks.length)];
        ArrayList<Perk> comp = first.getCompatiblePerks();

        item.addPerk(itemStack, first);

        item.addPerk(itemStack, comp.get(random.nextInt(comp.size())));

        return itemStack;
    }
}
