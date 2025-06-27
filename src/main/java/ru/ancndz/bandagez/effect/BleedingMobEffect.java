package ru.ancndz.bandagez.effect;

import static ru.ancndz.bandagez.effect.EffectParticlesHelper.addParticles;
import static ru.ancndz.bandagez.effect.ModEffects.BLEEDING_EFFECT_NAME;
import static ru.ancndz.bandagez.effect.ModEffects.HARD_BLEEDING_EFFECT_NAME;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import ru.ancndz.bandagez.mod.BandagezMod;
import ru.ancndz.bandagez.mod.BandagezModConfig;

import javax.annotation.Nullable;

public class BleedingMobEffect extends Effect implements EffectPriority {

    public static final int DAMAGE_INTERVAL = 40;

    private final boolean hard;

    @Nullable
    private String descriptionId;

    public BleedingMobEffect(boolean hard, EffectType category, int color) {
        super(category, color);
        this.hard = hard;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int food) {
        entity.hurt(DamageSource.MAGIC, hard ? 1.5F : 1.0F);
        if (Boolean.TRUE.equals(BandagezModConfig.CLIENT.showParticles.get())) {
            addParticles(entity, ParticleTypes.FALLING_LAVA);
        }
    }

    @Override
    public boolean isDurationEffectTick(int tick, int amplifier) {
        int j = DAMAGE_INTERVAL >> amplifier;
        return j == 0 || tick % j == 0;
    }

	@Override
    protected String getOrCreateDescriptionId() {
		if (this.descriptionId == null) {
			this.descriptionId = Util.makeDescriptionId("effect",
                    new ResourceLocation(BandagezMod.MODID,
                            hard ? HARD_BLEEDING_EFFECT_NAME : BLEEDING_EFFECT_NAME));
		}
		return this.descriptionId;
	}

    @Override
    public EffectPriorities getPriority() {
        return hard ? EffectPriorities.HARD_BLEEDING : EffectPriorities.BLEEDING;
    }
}
