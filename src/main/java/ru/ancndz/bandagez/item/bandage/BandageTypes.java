package ru.ancndz.bandagez.item.bandage;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.effect.Effects;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public enum BandageTypes implements BandageType {

	EMPTY(20, Collections.singletonList(Effects.BLEEDING.getHolder().orElseThrow())),

	SMALL(40, true, BandageTypes::isNotFullHealth,
			livingEntity -> livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0)),
			Collections.singletonList(Effects.BLEEDING.getHolder().orElseThrow())),

	MEDIUM(70, true, BandageTypes::isNotFullHealth,
			livingEntity -> livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1)),
			Collections.singletonList(Effects.BLEEDING.getHolder().orElseThrow())),

	LARGE(100, true, BandageTypes::isNotFullHealth,
			livingEntity -> livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2)),
			Collections.singletonList(Effects.BLEEDING.getHolder().orElseThrow())),

	HEMOSTATIC(40, BandageTypes::handleHardBleeding, Collections.singletonList(Effects.HARD_BLEEDING
			.getHolder().orElseThrow())),

    ANTI_BIOTIC(50, List.of(MobEffects.POISON)),

    MAGIC(50, List.of(MobEffects.WITHER)),

    STIMULANT(50, List.of(MobEffects.DIG_SLOWDOWN, MobEffects.MOVEMENT_SLOWDOWN)),

    ;

    private static void handleHardBleeding(LivingEntity livingEntity) {
		livingEntity.addEffect(new MobEffectInstance(Effects.FRESH_BANDAGE.getHolder().orElseThrow(), 800));
	}

	private static boolean isNotFullHealth(LivingEntity livingEntity) {
		return livingEntity.getHealth() < livingEntity.getMaxHealth();
    }

    private final int itemUseDuration;

	private final boolean healing;

    private final Predicate<LivingEntity> canUseItem;

    private final Consumer<LivingEntity> applyEffect;

	private final List<Holder<MobEffect>> removingEffects;

    BandageTypes(int itemUseDuration,
			boolean healing,
            Predicate<LivingEntity> canUseItem,
            Consumer<LivingEntity> applyEffect,
			List<Holder<MobEffect>> removingEffects) {
        this.itemUseDuration = itemUseDuration;
		this.healing = healing;
        this.canUseItem = canUseItem;
        this.applyEffect = applyEffect;
        this.removingEffects = removingEffects;
    }

	BandageTypes(int itemUseDuration, List<Holder<MobEffect>> removingEffects) {
		this(itemUseDuration, false, livingEntity -> removingEffects.stream().anyMatch(livingEntity::hasEffect),
				livingEntity -> {
				}, removingEffects);
    }

    BandageTypes(int itemUseDuration,
			Consumer<LivingEntity> applyEffect, List<Holder<MobEffect>> removingEffects) {
		this(itemUseDuration, false, livingEntity -> removingEffects.stream().anyMatch(livingEntity::hasEffect),
				applyEffect, removingEffects);
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
        removingEffects.stream().filter(livingEntity::hasEffect).forEach(livingEntity::removeEffect);
        applyEffect.accept(livingEntity);
    }

    @Override
	public List<Holder<MobEffect>> getRemovingEffects() {
        return removingEffects;
    }

	@Override
	public String getName() {
		return name().toLowerCase();
	}

	@Override
	public boolean isHealing() {
		return healing;
	}

}
