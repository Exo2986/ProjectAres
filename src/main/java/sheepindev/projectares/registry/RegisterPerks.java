package sheepindev.projectares.registry;

import com.mojang.brigadier.StringReader;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import sheepindev.projectares.network.EvasionPacketHandler;
import sheepindev.projectares.perk.*;

import java.util.Random;

import static sheepindev.projectares.util.RegistryHelper.prefix;
import static sheepindev.projectares.ProjectAres.LOGGER;

public class RegisterPerks {
    public static final RegistryKey<Registry<Perk>> PERK_KEY = RegistryKey.getOrCreateRootKey(prefix("perks"));
    public static IForgeRegistry<Perk> PERK_REGISTRY;

    public static void registerRegistry(RegistryEvent.NewRegistry event) {
        PERK_REGISTRY = new RegistryBuilder<Perk>()
                .setName(PERK_KEY.getLocation())
                .setType(Perk.class)
                .allowModification()
                .create();
    }

    public static Perk getRegisteredPerk(String name) {
        StringReader reader = new StringReader(name);
        try {
            return PERK_REGISTRY.getValue(ResourceLocation.read(reader));
        } catch (Exception e) {
            return null;
        }
    }

    public static Perk getRegisteredPerk(ResourceLocation name) {
        return getRegisteredPerk(name.toString());
    }

    public static Perk[] getRegisteredPerks() {
        return PERK_REGISTRY.getValues().toArray(new Perk[0]);
    }

    public static void registerPerks(RegistryEvent.Register<Perk> event) {
        LOGGER.debug("Registering perks!!");
        IForgeRegistry<Perk> registry = event.getRegistry();
        LOGGER.debug(registry == null);
        registry.register(new RampagePerk()         .setRegistryName("rampage"));
        registry.register(new VampirismPerk()       .setRegistryName("vampirism"));
        registry.register(new ThrillPerk()          .setRegistryName("thrill_of_the_kill"));
        registry.register(new TopHeavyPerk()        .setRegistryName("top_heavy"));
        registry.register(new ReachPerk()           .setRegistryName("extended_blade"));
        registry.register(new EternalPerk()         .setRegistryName("eternal"));
        registry.register(new AdrenalineRushPerk()  .setRegistryName("adrenaline_rush"));
        registry.register(new SurroundedPerk()      .setRegistryName("surrounded"));
        registry.register(new EvasionPerk()         .setRegistryName("evasion"));
        registry.register(new VortexStrikePerk()    .setRegistryName("vortex_strike"));
        registry.register(new CriticalSpecPerk()    .setRegistryName("critical_spec"));
        registry.register(new ConfusionPerk()       .setRegistryName("confusion"));
        registry.register(new MagneticPerk()        .setRegistryName("magnetic"));
        registry.register(new HolyFirePerk()        .setRegistryName("holy_fire"));
        registry.register(new CopperCoatingPerk()   .setRegistryName("copper_coating"));
        registry.register(new SoulCapacitorPerk()   .setRegistryName("soul_capacitor"));
        registry.register(new FreezingPerk()        .setRegistryName("freezing"));
        registry.register(new FinalStandPerk()      .setRegistryName("final_stand"));
        registry.register(new ChorusImbuedPerk()    .setRegistryName("chorus_imbued"));
        registry.register(new KillingWindPerk()     .setRegistryName("killing_wind"));
    }
}
