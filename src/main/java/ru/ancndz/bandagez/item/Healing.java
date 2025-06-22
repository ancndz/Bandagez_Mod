package ru.ancndz.bandagez.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

public interface Healing {

    static boolean isNotFullHealth(LivingEntity livingEntity) {
        return livingEntity.getHealth() < livingEntity.getMaxHealth();
    }

    Component getTooltipComponent();

    float getMaxHeal();
}
