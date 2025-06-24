package ru.ancndz.bandagez.item.bandage;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.item.ApplyingEffects;
import ru.ancndz.bandagez.item.RemovingEffects;
import ru.ancndz.bandagez.item.SupplyCustomTooltip;

import java.util.List;

public interface BandageType extends RemovingEffects, ApplyingEffects, SupplyCustomTooltip {

    int getUseDuration();

    boolean canUse(LivingEntity livingEntity);

    String getName();

    @Override
    default void addCustomTooltip(List<Component> components) {
        components.add(Component.translatable("bandagez.tooltip.removing_effects"));
        for (var effect : getRemovingEffects()) {
            final MobEffect mobEffect = effect.get();
            components.add(Component.translatable(mobEffect.getDescriptionId())
                .withStyle(mobEffect.getCategory().getTooltipFormatting()));
        }
    }
}
