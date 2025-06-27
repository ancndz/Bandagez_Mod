package ru.ancndz.bandagez.item.bandage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;
import ru.ancndz.bandagez.item.EffectProvider;
import ru.ancndz.bandagez.item.RemovingEffects;
import ru.ancndz.bandagez.item.SupplyCustomTooltip;

import java.util.List;

public interface BandageType extends RemovingEffects, EffectProvider, SupplyCustomTooltip {

    int getUseDuration();

    boolean canUse(PlayerEntity livingEntity);

    String getName();

    @Override
    default void addCustomTooltip(List<ITextComponent> components) {
        components.add(new TranslationTextComponent("bandagez.tooltip.removing_effects"));
        for (RegistryObject<Effect> effect : getRemovingEffects()) {
            components.add(new TranslationTextComponent(effect.get().getDescriptionId())
                    .withStyle(effect.get().getCategory().getTooltipFormatting()));
        }
    }
}
