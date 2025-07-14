package ru.ancndz.bandagez.effect.particle;

import net.minecraft.core.particles.SimpleParticleType;

public class ParticleTypeInstanceNeoForgeProvider implements ParticleTypeInstanceProvider {
    @Override
    public SimpleParticleType getSimple(boolean isAlwaysVisible) {
        return new SimpleParticleType(isAlwaysVisible);
    }
}
