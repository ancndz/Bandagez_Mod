package ru.ancndz.bandagez.item.bandage;

import static ru.ancndz.bandagez.effect.ModEffects.BLEEDING;
import static ru.ancndz.bandagez.effect.ModEffects.FRESH_BANDAGE;
import static ru.ancndz.bandagez.effect.ModEffects.HARD_BLEEDING;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import ru.ancndz.bandagez.item.RemovingEffects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Predicate;

public enum BandageTypes implements BandageType {

    EMPTY(20, Collections.singletonList(BLEEDING)),

    HEMOSTATIC(40, BandageTypes::handleHardBleeding, Collections.singletonList(HARD_BLEEDING)),

    ANTI_BIOTIC(50,
            Collections.singletonList(RegistryObject.of(Effects.POISON.getRegistryName(), ForgeRegistries.POTIONS))),

    MAGIC(50, Collections.singletonList(RegistryObject.of(Effects.WITHER.getRegistryName(), ForgeRegistries.POTIONS))),

    STIMULANT(50, Arrays.asList(RegistryObject.of(Effects.DIG_SLOWDOWN.getRegistryName(), ForgeRegistries.POTIONS),
            RegistryObject.of(Effects.MOVEMENT_SLOWDOWN.getRegistryName(), ForgeRegistries.POTIONS))),

    ;

    private static void handleHardBleeding(LivingEntity livingEntity) {
        livingEntity.addEffect(new EffectInstance(FRESH_BANDAGE.get(), 800));
	}

    private final int itemUseDuration;

    private final Predicate<LivingEntity> canUseItem;

    private final Consumer<LivingEntity> applyEffect;

    private final List<RegistryObject<Effect>> removingEffects;

    BandageTypes(int itemUseDuration,
            Predicate<LivingEntity> canUseItem,
            Consumer<LivingEntity> applyEffect,
            List<RegistryObject<Effect>> removingEffects) {
        this.itemUseDuration = itemUseDuration;
        this.canUseItem = canUseItem;
        this.applyEffect = applyEffect;
        this.removingEffects = removingEffects;
    }

    BandageTypes(int itemUseDuration,
            Consumer<LivingEntity> applyEffect,
            List<RegistryObject<Effect>> removingEffects) {
        this(itemUseDuration,
                livingEntity -> RemovingEffects.hasAnyOf(livingEntity, removingEffects),
                applyEffect,
                removingEffects);
    }

    BandageTypes(int itemUseDuration, List<RegistryObject<Effect>> removingEffects) {
        this(itemUseDuration, livingEntity -> {
        }, removingEffects);
    }


    @Override
    public int getUseDuration() {
        return itemUseDuration;
    }

    @Override
    public boolean canUse(PlayerEntity livingEntity) {
        return canUseItem.test(livingEntity);
    }

    @Override
    public void apply(LivingEntity livingEntity) {
        BandageType.super.removeEffects(livingEntity);
        applyEffect.accept(livingEntity);
    }

    @Override
    public List<RegistryObject<Effect>> getRemovingEffects() {
        return removingEffects;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

}
