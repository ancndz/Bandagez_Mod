package ru.ancndz.bandagez.item.bandage;

import net.minecraft.potion.EffectType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import ru.ancndz.bandagez.item.Healing;

import java.util.List;

public interface HealingBandageType extends BandageType, Healing {

    @Override
    default void addCustomTooltip(List<ITextComponent> components) {
        BandageType.super.addCustomTooltip(components);
        components.add(ITextComponent.nullToEmpty(""));
        components.add(new TranslationTextComponent("bandagez.tooltip.healing", getMaxHeal())
                .withStyle(EffectType.BENEFICIAL.getTooltipFormatting()));
    }

}
