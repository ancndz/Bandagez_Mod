package ru.ancndz.bandagez.effect;

import static ru.ancndz.bandagez.effect.EffectParticlesHelper.addParticles;
import static ru.ancndz.bandagez.effect.ModMobEffects.BLEEDING_EFFECT_NAME;
import static ru.ancndz.bandagez.effect.ModMobEffects.HARD_BLEEDING_EFFECT_NAME;

import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import ru.ancndz.bandagez.BandagezMod;
import ru.ancndz.bandagez.effect.particle.ModParticles;
import ru.ancndz.bandagez.config.ModConfiguration;

public class BleedingMobEffect extends MobEffect implements EffectPriority {

    public static final int DAMAGE_INTERVAL = 40;

    private final boolean hard;

    private String descriptionId;

    public BleedingMobEffect(boolean hard, MobEffectCategory category, int color) {
        super(category, color);
        this.hard = hard;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int food) {
        entity.hurt(entity.damageSources().magic(), hard ? 1.5F : 1.0F);
        if (Boolean.TRUE.equals(ModConfiguration.getClientConfig().getShowParticles())) {
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
                ResourceLocation.tryBuild(BandagezMod.MODID,
                            hard ? HARD_BLEEDING_EFFECT_NAME : BLEEDING_EFFECT_NAME));
		}
		return this.descriptionId;
	}

    @Override
    public EffectPriorities getPriority() {
        return hard ? EffectPriorities.HARD_BLEEDING : EffectPriorities.BLEEDING;
    }
}
