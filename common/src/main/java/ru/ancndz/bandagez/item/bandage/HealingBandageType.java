package ru.ancndz.bandagez.item.bandage;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import ru.ancndz.bandagez.item.Healing;

import java.util.List;

public interface HealingBandageType extends BandageType, Healing {

    @Override
    default void addCustomTooltip(List<Component> components) {
        BandageType.super.addCustomTooltip(components);
        components.add(Component.empty());
        components.add(Component.translatable("bandagez.tooltip.healing", String.format("%.0f", getMaxHeal()))
                .withStyle(MobEffectCategory.BENEFICIAL.getTooltipFormatting()));
    }
}
