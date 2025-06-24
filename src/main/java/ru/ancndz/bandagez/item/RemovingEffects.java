package ru.ancndz.bandagez.item;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface RemovingEffects {

    static boolean hasAnyOf(LivingEntity livingEntity, List<Holder<MobEffect>> removingEffects) {
        return removingEffects.stream().anyMatch(livingEntity::hasEffect);
    }

    List<Holder<MobEffect>> getRemovingEffects();
}
