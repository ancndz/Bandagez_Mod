package ru.ancndz.bandagez.effect.particle;

import net.minecraft.core.particles.SimpleParticleType;

public interface ParticleTypeInstanceProvider {

    SimpleParticleType getSimple(boolean isAlwaysVisible);

}
