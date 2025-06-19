package ru.ancndz.bandagez.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;

public class EffectParticlesHelper {

	public static void addParticles(LivingEntity entity, ParticleOptions particleOptions, int count) {
		BlockPos blockPos = entity.getOnPos();
		for (int i = 0; i < count; i++) {
			double x = blockPos.getX() + entity.getRandom().nextDouble();
			double y = blockPos.getY() + 1.7D;
			double z = blockPos.getZ() + entity.getRandom().nextDouble();
			double d0 = entity.getRandom().nextGaussian() * 0.02D;
			double d1 = entity.getRandom().nextGaussian() * 0.02D;
			double d2 = entity.getRandom().nextGaussian() * 0.02D;
			entity.level().addParticle(particleOptions, x, y, z, d0, d1, d2);
		}
	}

}
