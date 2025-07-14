package ru.ancndz.bandagez.item.bandage;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.item.EffectProvider;
import ru.ancndz.bandagez.item.RemovingEffects;
import ru.ancndz.bandagez.item.SupplyCustomTooltip;

import java.util.List;

public interface BandageType extends RemovingEffects, EffectProvider, SupplyCustomTooltip {

    int getUseDuration();

    boolean canUse(LivingEntity livingEntity);

    String getName();

    @Override
    default void addCustomTooltip(List<Component> components) {
        components.add(Component.translatable("bandagez.tooltip.removing_effects"));
        for (var effect : getRemovingEffects()) {
            components.add(Component.translatable(effect.get().getDescriptionId())
                    .withStyle(effect.get().getCategory().getTooltipFormatting()));
        }
    }
}
