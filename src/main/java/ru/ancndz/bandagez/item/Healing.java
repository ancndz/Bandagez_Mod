package ru.ancndz.bandagez.item;

import net.minecraft.world.entity.LivingEntity;

public interface Healing extends ApplyingEffects {

    static boolean isNotFullHealth(LivingEntity livingEntity) {
        return livingEntity.getHealth() < livingEntity.getMaxHealth();
    }

    float getMaxHeal();

}
