package ru.ancndz.bandagez.item;

import net.minecraft.world.entity.LivingEntity;

public interface BandageType {

	int getUseDuration();

	boolean canUse(LivingEntity livingEntity);

	void applyEffects(LivingEntity livingEntity);

	default void applyAfterEffects(LivingEntity livingEntity) {
    }

}
