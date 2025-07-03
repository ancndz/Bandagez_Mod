package ru.ancndz.bandagez.effect.particle;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import ru.ancndz.bandagez.BandagezMod;
import ru.ancndz.bandagez.platform.PlatformServices;
import ru.ancndz.bandagez.registration.RegistrationProvider;
import ru.ancndz.bandagez.registration.RegistryObject;

public class ModParticles {

    public static void init() {
    }

    private static final ParticleTypeInstanceProvider TYPE_INSTANCE_PROVIDER = PlatformServices.load(ParticleTypeInstanceProvider.class);

    /**
     * The provider for items
     */
    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES =
        RegistrationProvider.get(BuiltInRegistries.PARTICLE_TYPE, BandagezMod.MODID);

    public static final RegistryObject<SimpleParticleType> BLEEDING_PARTICLE = PARTICLE_TYPES.register("bleeding",
        () -> TYPE_INSTANCE_PROVIDER.getSimple(false));
}
