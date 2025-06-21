package ru.ancndz.bandagez.item.bandage;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.effect.Effects;
import ru.ancndz.bandagez.item.Healing;

import java.util.Collections;
import java.util.List;

public enum HealingBandageTypes implements BandageType, Healing {

    SMALL("small_band", 40, 100, 0, 2F, Collections.singletonList(Effects.BLEEDING.getHolder().orElseThrow())),

    MEDIUM("medium_band", 70, 100, 1, 4F, Collections.singletonList(Effects.BLEEDING.getHolder().orElseThrow())),

    LARGE("large_band", 100, 100, 2, 8F, Collections.singletonList(Effects.BLEEDING.getHolder().orElseThrow()))

    ;

    private final String name;

    private final int itemUseDuration;

    private final int healingDuration;

    private final int amplifier;

    private final float maxHeal;

    private final List<Holder<MobEffect>> removingEffects;

    HealingBandageTypes(String name,
            int itemUseDuration,
            int healingDuration,
            int amplifier,
            float maxHeal,
            List<Holder<MobEffect>> removingEffects) {
        this.name = name;
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
    public boolean canUse(LivingEntity livingEntity) {
        return Healing.isNotFullHealth(livingEntity);
    }

    @Override
    public void applyEffects(LivingEntity livingEntity) {
        removingEffects.stream().filter(livingEntity::hasEffect).forEach(livingEntity::removeEffect);
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, healingDuration, amplifier));
    }

    @Override
    public List<Holder<MobEffect>> getRemovingEffects() {
        return removingEffects;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getMaxHeal() {
        return maxHeal;
    }

}
