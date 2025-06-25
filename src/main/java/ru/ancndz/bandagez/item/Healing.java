package ru.ancndz.bandagez.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public interface Healing extends EffectProvider {

    static boolean isNotFullHealth(LivingEntity livingEntity) {
        return livingEntity.getHealth() < livingEntity.getMaxHealth();
    }

    float getMaxHeal();

    MobEffectInstance getHealingInstance();

    @Override
    default void apply(LivingEntity livingEntity) {
        livingEntity.addEffect(getHealingInstance());
    }
}
