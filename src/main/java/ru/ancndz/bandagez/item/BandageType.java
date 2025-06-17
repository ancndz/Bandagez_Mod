package ru.ancndz.bandagez.item;

import java.util.Collections;
import java.util.List;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

public interface BandageType {

	int getUseDuration();

	boolean canUse(LivingEntity livingEntity);

	void applyEffects(LivingEntity livingEntity);

	default void applyAfterEffects(LivingEntity livingEntity) {
    }

	List<MobEffect> getRemovingEffects();

}
