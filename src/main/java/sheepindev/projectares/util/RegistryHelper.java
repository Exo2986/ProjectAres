package sheepindev.projectares.util;

import net.minecraft.util.ResourceLocation;

import static sheepindev.projectares.ProjectAres.MOD_ID;

public class RegistryHelper {
    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
