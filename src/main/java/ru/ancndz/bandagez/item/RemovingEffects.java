package ru.ancndz.bandagez.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Optional;

public interface RemovingEffects {

    static boolean hasAnyOf(LivingEntity livingEntity, List<RegistryObject<MobEffect>> removingEffects) {
        return removingEffects.stream()
                .map(RegistryObject::getHolder)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .anyMatch(livingEntity::hasEffect);
    }

    default void removeEffects(LivingEntity livingEntity) {
        getRemovingEffects().stream()
                .map(RegistryObject::getHolder)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(livingEntity::hasEffect)
                .forEach(livingEntity::removeEffect);
    }

    List<RegistryObject<MobEffect>> getRemovingEffects();
}
