//package sheepindev.projectares.registry;
//
//import net.minecraft.particles.ParticleType;
//import net.minecraftforge.event.RegistryEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import sheepindev.projectares.particle.VortexParticleData;
//import sheepindev.projectares.particle.VortexParticleType;
//
//import static sheepindev.projectares.util.RegistryHelper.prefix;
//
//public class RegisterParticles {
//    public static ParticleType<VortexParticleData> vortexParticleType;
//
//    @SubscribeEvent
//    public static void onParticleTypeRegistration(RegistryEvent.Register<ParticleType<?>> event) {
//        vortexParticleType = new VortexParticleType();
//        vortexParticleType.setRegistryName(prefix("vortex_particle"));
//        event.getRegistry().register(vortexParticleType);
//    }
//}
