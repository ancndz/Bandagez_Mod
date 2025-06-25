package ru.ancndz.bandagez.item;

import net.minecraft.network.chat.Component;

import java.util.List;

public interface SupplyCustomTooltip {

    default void addCustomTooltip(List<Component> components) {
    }
}
