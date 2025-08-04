package ru.ancndz.bandagez.effect.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

public class ParticleTypeInstanceFabricProvider implements ParticleTypeInstanceProvider {
    @Override
    public SimpleParticleType getSimple(boolean isAlwaysVisible) {
        return FabricParticleTypes.simple(isAlwaysVisible);
    }
}
