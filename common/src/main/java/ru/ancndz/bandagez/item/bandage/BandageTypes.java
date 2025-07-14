package ru.ancndz.bandagez.item.bandage;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.effect.ModMobEffects;
import ru.ancndz.bandagez.item.RemovingEffects;
import ru.ancndz.bandagez.registration.RegistryObject;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;

public enum BandageTypes implements BandageType {

    EMPTY(20, Collections.singletonList(ModMobEffects.BLEEDING)),

    HEMOSTATIC(40, BandageTypes::handleHardBleeding, Collections.singletonList(ModMobEffects.HARD_BLEEDING)),

    ANTI_BIOTIC(50, List.of(RegistryObject.of(BuiltInRegistries.MOB_EFFECT, MobEffects.POISON))),

    MAGIC(50,
        List.of(RegistryObject.of(BuiltInRegistries.MOB_EFFECT, MobEffects.WITHER))),

    STIMULANT(50, List.of(RegistryObject.of(BuiltInRegistries.MOB_EFFECT, MobEffects.DIG_SLOWDOWN),
        RegistryObject.of(BuiltInRegistries.MOB_EFFECT, MobEffects.MOVEMENT_SLOWDOWN)))

    ;

    private static void handleHardBleeding(LivingEntity livingEntity) {
		livingEntity.addEffect(new MobEffectInstance(ModMobEffects.FRESH_BANDAGE.get(), 800));
	}

    private final int itemUseDuration;

    private final Predicate<LivingEntity> canUseItem;

    private final Consumer<LivingEntity> applyEffect;

    private final List<RegistryObject<MobEffect>> removingEffects;

    BandageTypes(int itemUseDuration,
            Predicate<LivingEntity> canUseItem,
            Consumer<LivingEntity> applyEffect,
            List<RegistryObject<MobEffect>> removingEffects) {
        this.itemUseDuration = itemUseDuration;
        this.canUseItem = canUseItem;
        this.applyEffect = applyEffect;
        this.removingEffects = removingEffects;
    }

    BandageTypes(int itemUseDuration,
            Consumer<LivingEntity> applyEffect,
            List<RegistryObject<MobEffect>> removingEffects) {
        this(itemUseDuration,
                livingEntity -> RemovingEffects.hasAnyOf(livingEntity, removingEffects),
                applyEffect,
                removingEffects);
    }

    BandageTypes(int itemUseDuration, List<RegistryObject<MobEffect>> removingEffects) {
        this(itemUseDuration, livingEntity -> {
        }, removingEffects);
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
    public void apply(LivingEntity livingEntity) {
        BandageType.super.removeEffects(livingEntity);
        applyEffect.accept(livingEntity);
    }

    @Override
    public List<RegistryObject<MobEffect>> getRemovingEffects() {
        return removingEffects;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

}
