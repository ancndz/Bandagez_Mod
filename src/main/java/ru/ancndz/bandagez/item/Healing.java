package ru.ancndz.bandagez.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;

public interface Healing extends EffectProvider {

    static boolean isNotFullHealth(LivingEntity livingEntity) {
        return livingEntity.getHealth() < livingEntity.getMaxHealth();
    }

    float getMaxHeal();

    EffectInstance getHealingInstance();

    @Override
    default void apply(LivingEntity livingEntity) {
        livingEntity.addEffect(getHealingInstance());
    }
}
