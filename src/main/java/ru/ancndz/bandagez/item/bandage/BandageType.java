package ru.ancndz.bandagez.item.bandage;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface BandageType {

	int getUseDuration();

	boolean canUse(LivingEntity livingEntity);

	void applyEffects(LivingEntity livingEntity);

	default void applyAfterEffects(LivingEntity livingEntity) {
    }

	List<MobEffect> getRemovingEffects();

	String getName();
}
