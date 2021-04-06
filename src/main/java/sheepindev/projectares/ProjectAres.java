package sheepindev.projectares;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sheepindev.projectares.network.ProjectAresPacketHandler;
import sheepindev.projectares.perk.PerkRegistry;
import sheepindev.projectares.registry.RegisterEffects;
import sheepindev.projectares.registry.RegisterItems;
import sheepindev.projectares.util.PerkEventHandler;

@Mod("projectares")
public class ProjectAres {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "projectares";
    public static ProjectAresPacketHandler PACKET_HANDLER;

    public ProjectAres() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        RegisterItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        RegisterEffects.EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PerkEventHandler());


    }

    private void setup(final FMLCommonSetupEvent event) {
        PerkRegistry.Init();
        ProjectAresPacketHandler.SetupPackets();
    }

    private void clientSetup(final FMLClientSetupEvent event) {}
}
