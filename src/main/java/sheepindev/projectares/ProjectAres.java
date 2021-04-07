package sheepindev.projectares;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sheepindev.projectares.network.ProjectAresPacketHandler;
import sheepindev.projectares.perk.Perk;
import sheepindev.projectares.registry.RegisterCommands;
import sheepindev.projectares.registry.RegisterEffects;
import sheepindev.projectares.registry.RegisterItems;
import sheepindev.projectares.registry.RegisterPerks;
import sheepindev.projectares.util.PerkEventHandler;

@Mod("projectares")
public class ProjectAres {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "projectares";
    public static ProjectAresPacketHandler PACKET_HANDLER;

    public ProjectAres() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::setup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(RegisterPerks::registerRegistry);
        modBus.addGenericListener(Perk.class, RegisterPerks::registerPerks);

        RegisterItems.ITEMS.register(modBus);
        RegisterEffects.EFFECTS.register(modBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PerkEventHandler());
        MinecraftForge.EVENT_BUS.register(RegisterCommands.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ProjectAresPacketHandler.setupPackets();
    }

    private void clientSetup(final FMLClientSetupEvent event) {}
}