package ru.ancndz.bandagez.item.bandage;

import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.item.RemovingEffects;

public interface BandageType extends RemovingEffects {

    int getUseDuration();

    boolean canUse(LivingEntity livingEntity);

    void applyEffects(LivingEntity livingEntity);

    default void applyAfterEffects(LivingEntity livingEntity) {
    }

    String getName();
}
