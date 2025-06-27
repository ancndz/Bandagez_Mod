package ru.ancndz.bandagez.item.bandage;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.ancndz.bandagez.effect.ModMobEffects;
import ru.ancndz.bandagez.item.RemovingEffects;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;

public enum BandageTypes implements BandageType {

    EMPTY(20, Collections.singletonList(ModMobEffects.BLEEDING)),

    HEMOSTATIC(40, BandageTypes::handleHardBleeding, Collections.singletonList(ModMobEffects.HARD_BLEEDING)),

    ANTI_BIOTIC(50,
            List.of(RegistryObject.create(ResourceLocation.parse(MobEffects.POISON.getRegisteredName()),
                    ForgeRegistries.MOB_EFFECTS))),

    MAGIC(50,
            List.of(RegistryObject.create(ResourceLocation.parse(MobEffects.WITHER.getRegisteredName()),
                    ForgeRegistries.MOB_EFFECTS))),

    STIMULANT(50,
            List.of(RegistryObject.create(ResourceLocation.parse(MobEffects.DIG_SLOWDOWN.getRegisteredName()),
                    ForgeRegistries.MOB_EFFECTS),
                    RegistryObject.create(ResourceLocation.parse(MobEffects.MOVEMENT_SLOWDOWN.getRegisteredName()),
                            ForgeRegistries.MOB_EFFECTS))),

    ;

    private static void handleHardBleeding(LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(ModMobEffects.FRESH_BANDAGE.getHolder().orElseThrow(), 800));
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
