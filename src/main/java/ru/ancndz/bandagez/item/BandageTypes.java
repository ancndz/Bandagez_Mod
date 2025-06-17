package ru.ancndz.bandagez.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public enum BandageTypes implements BandageType {

    EMPTY(20, Collections.singletonList(BandagezMod.BLEEDING.get())),

    HEMOSTATIC(40, BandageTypes::handleHardBleeding, Collections.singletonList(BandagezMod.HARD_BLEEDING.get())),

    SMALL(40, livingEntity -> livingEntity.getHealth() < livingEntity.getMaxHealth(), livingEntity -> {
        EMPTY.applyEffects(livingEntity);
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
    }),
    MEDIUM(70, livingEntity -> livingEntity.getHealth() < livingEntity.getMaxHealth(), livingEntity -> {
        EMPTY.applyEffects(livingEntity);
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 1));
    }),
    LARGE(100, livingEntity -> livingEntity.getHealth() < livingEntity.getMaxHealth(), livingEntity -> {
        EMPTY.applyEffects(livingEntity);
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 2));
    }),

    ANTI_BIOTIC(50, List.of(MobEffects.POISON)),
    MAGIC(50, List.of(MobEffects.WITHER)),
    STIMULANT(50, List.of(MobEffects.DIG_SLOWDOWN, MobEffects.MOVEMENT_SLOWDOWN)),

    ;

    private static void handleHardBleeding(LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(BandagezMod.FRESH_BANDAGE.get(), 800));
    }

    private final int itemUseDuration;

    private final Predicate<LivingEntity> canUseItem;

    private final Consumer<LivingEntity> applyEffect;

    private final List<MobEffect> removingEffects;

    BandageTypes(int itemUseDuration, Predicate<LivingEntity> canUseItem, Consumer<LivingEntity> applyEffect) {
        this.itemUseDuration = itemUseDuration;
        this.canUseItem = canUseItem;
        this.applyEffect = applyEffect;
        this.removingEffects = Collections.emptyList();
    }

    BandageTypes(int itemUseDuration,
            Predicate<LivingEntity> canUseItem,
            Consumer<LivingEntity> applyEffect,
            List<MobEffect> removingEffects) {
        this.itemUseDuration = itemUseDuration;
        this.canUseItem = canUseItem;
        this.applyEffect = applyEffect;
        this.removingEffects = removingEffects;
    }

    BandageTypes(int itemUseDuration, List<MobEffect> removingEffects) {
        this.itemUseDuration = itemUseDuration;
        this.canUseItem = livingEntity -> removingEffects.stream().anyMatch(livingEntity::hasEffect);
        this.applyEffect = livingEntity -> {
        };
        this.removingEffects = removingEffects;
    }

    BandageTypes(int itemUseDuration,
                 Consumer<LivingEntity> applyEffect, List<MobEffect> removingEffects) {
        this.itemUseDuration = itemUseDuration;
        this.canUseItem = livingEntity -> removingEffects.stream().anyMatch(livingEntity::hasEffect);
        this.applyEffect = applyEffect;
        this.removingEffects = removingEffects;
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
    public List<MobEffect> getRemovingEffects() {
        return removingEffects;
    }

}
