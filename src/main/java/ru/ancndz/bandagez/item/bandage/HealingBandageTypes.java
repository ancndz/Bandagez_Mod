package ru.ancndz.bandagez.item.bandage;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.effect.Effects;
import ru.ancndz.bandagez.item.Healing;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public enum HealingBandageTypes implements BandageType, Healing {

    SMALL(40, 100, 0, 2F, Collections.singletonList(Effects.BLEEDING.get())),

    MEDIUM(70, 100, 1, 4F, Collections.singletonList(Effects.BLEEDING.get())),

    LARGE(100, 100, 2, 8F, Collections.singletonList(Effects.BLEEDING.get()))

    ;

    private final int itemUseDuration;

    private final int healingDuration;

    private final int amplifier;

    private final float maxHeal;

    private final List<MobEffect> removingEffects;

    HealingBandageTypes(int itemUseDuration,
            int healingDuration,
            int amplifier,
            float maxHeal,
            List<MobEffect> removingEffects) {
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
    public List<MobEffect> getRemovingEffects() {
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
