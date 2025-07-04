package ru.ancndz.bandagez.effect;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;

public class EffectParticlesHelper {

	public static void addParticles(LivingEntity entity, ParticleOptions particleOptions) {
		int j = entity.isInvisible() ? 15 : 4;
		for (int i = 0; i < j; i++) {
			entity.level().addParticle(particleOptions, entity.getRandomX(0.5), entity.getRandomY(), entity.getRandomZ(0.5), 0, 0, 0);
		}
	}

}
