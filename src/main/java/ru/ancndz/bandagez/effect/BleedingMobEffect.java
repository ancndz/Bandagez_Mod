package ru.ancndz.bandagez.effect;

import static ru.ancndz.bandagez.effect.EffectParticlesHelper.addParticles;
import static ru.ancndz.bandagez.effect.Effects.BLEEDING_EFFECT_NAME;
import static ru.ancndz.bandagez.effect.Effects.HARD_BLEEDING_EFFECT_NAME;

import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.ancndz.bandagez.mod.BandagezMod;

import javax.annotation.Nullable;

public class BleedingMobEffect extends MobEffect implements EffectPriority {

    public static final int DAMAGE_INTERVAL = 40;

    private final boolean hard;

    @Nullable
    private String descriptionId;

    public BleedingMobEffect(boolean hard, MobEffectCategory category, int color) {
        super(category, color);
        this.hard = hard;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int food) {
        entity.hurt(entity.damageSources().magic(), hard ? 1.5F : 1.0F);
        addParticles(entity, ParticleTypes.FALLING_LAVA, hard ? 5 : 3);
    }

    @Override
    public boolean isDurationEffectTick(int tick, int amplifier) {
        int j = DAMAGE_INTERVAL >> amplifier;
        return j == 0 || tick % j == 0;
    }

	@Override
	protected @NotNull String getOrCreateDescriptionId() {
		if (this.descriptionId == null) {
			this.descriptionId = Util.makeDescriptionId("effect",
                    ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID,
                            hard ? HARD_BLEEDING_EFFECT_NAME : BLEEDING_EFFECT_NAME));
		}
		return this.descriptionId;
	}

    @Override
    public EffectPriorities getPriority() {
        return hard ? EffectPriorities.HARD_BLEEDING : EffectPriorities.BLEEDING;
    }
}
