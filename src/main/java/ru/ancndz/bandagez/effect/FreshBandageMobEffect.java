package ru.ancndz.bandagez.effect;

import static ru.ancndz.bandagez.effect.EffectParticlesHelper.addParticles;
import static ru.ancndz.bandagez.effect.ModEffects.FRESH_BANDAGE_EFFECT_NAME;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import ru.ancndz.bandagez.mod.BandagezMod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FreshBandageMobEffect extends Effect implements EffectPriority {

	public static final int TICK_RATE = 40;

	public static final float CHANCE_TO_BLEED = 0.2F;

    @Nullable
    private String descriptionId;

    public FreshBandageMobEffect(EffectType category, int color) {
		super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int food) {
        if (entity.isSprinting() && entity.getRandom().nextFloat() < CHANCE_TO_BLEED) {
            entity.addEffect(new EffectInstance(ModEffects.BLEEDING.get(), 400, 0, false, false, true));
			entity.removeEffect(ModEffects.FRESH_BANDAGE.get());
        }
        addParticles(entity, new BlockParticleData(ParticleTypes.FALLING_DUST, Blocks.COBWEB.defaultBlockState()));
    }

    @Override
    public boolean isDurationEffectTick(int tick, int amplifier) {
        int j = TICK_RATE >> amplifier;
		return j == 0 || tick % j == 0;
    }

    @Override
    protected @Nonnull String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("effect",
                    new ResourceLocation(BandagezMod.MODID, FRESH_BANDAGE_EFFECT_NAME));
        }
        return this.descriptionId;
    }

    @Override
    public EffectPriorities getPriority() {
        return EffectPriorities.FRESH_BANDAGE;
    }
}
