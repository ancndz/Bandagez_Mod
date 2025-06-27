package ru.ancndz.bandagez.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;

public interface RemovingEffects {

    static boolean hasAnyOf(LivingEntity livingEntity, List<RegistryObject<Effect>> removingEffects) {
        return removingEffects.stream().map(RegistryObject::get).anyMatch(livingEntity::hasEffect);
    }

    default void removeEffects(LivingEntity livingEntity) {
        getRemovingEffects().stream().map(RegistryObject::get).filter(livingEntity::hasEffect).forEach(livingEntity::removeEffect);
    }

    List<RegistryObject<Effect>> getRemovingEffects();
}
