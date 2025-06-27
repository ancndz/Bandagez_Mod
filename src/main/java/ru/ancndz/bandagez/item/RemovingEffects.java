package ru.ancndz.bandagez.item;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public interface RemovingEffects {

    static boolean hasAnyOf(LivingEntity livingEntity, List<RegistryObject<MobEffect>> removingEffects) {
        return removingEffects.stream().map(RegistryObject::get).anyMatch(livingEntity::hasEffect);
    }

    default void removeEffects(LivingEntity livingEntity) {
        getRemovingEffects().stream()
                .map(RegistryObject::get)
                .filter(livingEntity::hasEffect)
                .forEach(livingEntity::removeEffect);
    }

    List<RegistryObject<MobEffect>> getRemovingEffects();
}
