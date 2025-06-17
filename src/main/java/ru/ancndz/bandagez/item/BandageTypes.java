package ru.ancndz.bandagez.item;

import java.util.function.Consumer;
import java.util.function.Predicate;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public enum BandageTypes implements BandageType {

	BASE(20, livingEntity -> livingEntity.removeEffect()),
	SMALL(40, livingEntity -> livingEntity.getHealth() < livingEntity.getMaxHealth(),
			livingEntity -> livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0))),
	MEDIUM(70, livingEntity -> livingEntity.getHealth() < livingEntity.getMaxHealth(),
			livingEntity -> livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1))),
	LARGE(100, livingEntity -> livingEntity.getHealth() < livingEntity.getMaxHealth(),
			livingEntity -> livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2)))

    ;

	private final int itemUseDuration;

	private final Predicate<LivingEntity> canUseItem;

	private final Consumer<LivingEntity> applyEffect;

	BandageTypes(int itemUseDuration, Predicate<LivingEntity> canUseItem, Consumer<LivingEntity> applyEffect) {
		this.itemUseDuration = itemUseDuration;
		this.canUseItem = canUseItem;
		this.applyEffect = applyEffect;
	}

	@Override
	public int getUseDuration() {
		return itemUseDuration;
	}

	@Override
	public boolean canUse(LivingEntity livingEntity) {
		return canUseItem.test(livingEntity);
	}

	@Override
	public void applyEffects(LivingEntity livingEntity) {
		applyEffect.accept(livingEntity);
	}
}
