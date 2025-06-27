package ru.ancndz.bandagez.item;

import net.minecraft.util.text.ITextComponent;

import java.util.List;

public interface SupplyCustomTooltip {

    default void addCustomTooltip(List<ITextComponent> components) {
    }
}
