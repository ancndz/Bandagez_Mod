package ru.ancndz.bandagez.item.bandage;

import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.item.ApplyingEffects;
import ru.ancndz.bandagez.item.RemovingEffects;

public interface BandageType extends RemovingEffects, ApplyingEffects {

    int getUseDuration();

    boolean canUse(LivingEntity livingEntity);

    String getName();
}
