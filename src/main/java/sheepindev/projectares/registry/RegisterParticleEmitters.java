package sheepindev.projectares.registry;

import com.mojang.brigadier.StringReader;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import sheepindev.projectares.particle.FreezingParticleEmitter;
import sheepindev.projectares.particle.ParticleEmitter;
import sheepindev.projectares.particle.VortexStrikeParticleEmitter;

import static sheepindev.projectares.ProjectAres.LOGGER;
import static sheepindev.projectares.util.RegistryHelper.prefix;

public class RegisterParticleEmitters {
    public static final RegistryKey<Registry<ParticleEmitter>> EMITTER_KEY = RegistryKey.getOrCreateRootKey(prefix("particle_emitters"));
    public static IForgeRegistry<ParticleEmitter> EMITTER_REGISTRY;

    public static void registerRegistry(RegistryEvent.NewRegistry event) {
        EMITTER_REGISTRY = new RegistryBuilder<ParticleEmitter>()
                .setName(EMITTER_KEY.getLocation())
                .setType(ParticleEmitter.class)
                .allowModification()
                .create();
    }

    public static ParticleEmitter getRegisteredEmitter(String name) {
        StringReader reader = new StringReader(name);
        try {
            return EMITTER_REGISTRY.getValue(ResourceLocation.read(reader));
        } catch (Exception e) {
            return null;
        }
    }

    public static ParticleEmitter getRegisteredEmitter(ResourceLocation name) {
        return getRegisteredEmitter(name.toString());
    }

    public static ParticleEmitter[] getRegisteredEmitters() {
        return EMITTER_REGISTRY.getValues().toArray(new ParticleEmitter[0]);
    }

    public static void registerEmitters(RegistryEvent.Register<ParticleEmitter> event) {
        LOGGER.debug("Registering emitters!!");
        IForgeRegistry<ParticleEmitter> registry = event.getRegistry();
        registry.register(new VortexStrikeParticleEmitter()           .setRegistryName("vortex_strike_particle_emitter"));
        registry.register(new FreezingParticleEmitter()               .setRegistryName("freezing_particle_emitter"));
    }
}
