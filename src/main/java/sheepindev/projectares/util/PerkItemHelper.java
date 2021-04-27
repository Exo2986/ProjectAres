package sheepindev.projectares.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import sheepindev.projectares.item.PerkItem;

import static sheepindev.projectares.util.RegistryHelper.prefix;

public class PerkItemHelper {
    public static boolean isPerkItemAndHasPerk(ItemStack stack, ResourceLocation perk) {
        if (stack.getItem() instanceof PerkItem) {
            PerkItem item = (PerkItem) stack.getItem();

            return item.hasPerk(stack, perk.toString());
        }
        return false;
    }

    public static boolean isReachable(PlayerEntity player, Vector3d pos) {
        return  false;
    }
}
