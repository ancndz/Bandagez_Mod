package ru.ancndz.bandagez.item.bandage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.fml.RegistryObject;
import ru.ancndz.bandagez.effect.ModEffects;
import ru.ancndz.bandagez.item.Healing;
import ru.ancndz.bandagez.item.RemovingEffects;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public enum HealingBandageTypes implements HealingBandageType {

    SMALL(40, 100, 0, 2F, Collections.singletonList(ModEffects.BLEEDING)),

    MEDIUM(70, 100, 1, 4F, Collections.singletonList(ModEffects.BLEEDING)),

    LARGE(100, 100, 2, 8F, Collections.singletonList(ModEffects.BLEEDING))

    ;

    private final int itemUseDuration;

    private final int healingDuration;

    private final int amplifier;

    private final float maxHeal;

    private final List<RegistryObject<Effect>> removingEffects;

    HealingBandageTypes(int itemUseDuration,
            int healingDuration,
            int amplifier,
            float maxHeal,
            List<RegistryObject<Effect>> removingEffects) {
        this.itemUseDuration = itemUseDuration;
        this.healingDuration = healingDuration;
        this.amplifier = amplifier;
        this.maxHeal = maxHeal;
        this.removingEffects = removingEffects;
    }

    @Override
    public int getUseDuration() {
        return itemUseDuration;
    }

    @Override
    public boolean canUse(PlayerEntity livingEntity) {
        return Healing.isNotFullHealth(livingEntity) || RemovingEffects.hasAnyOf(livingEntity, removingEffects);
    }

    @Override
    public EffectInstance getHealingInstance() {
        return new EffectInstance(net.minecraft.potion.Effects.REGENERATION, healingDuration, amplifier);
    }

    @Override
    public List<RegistryObject<Effect>> getRemovingEffects() {
        return removingEffects;
    }

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public float getMaxHeal() {
        return maxHeal;
    }


}
