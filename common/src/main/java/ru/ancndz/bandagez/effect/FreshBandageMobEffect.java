package ru.ancndz.bandagez.effect;

import static ru.ancndz.bandagez.effect.EffectParticlesHelper.addParticles;
import static ru.ancndz.bandagez.effect.ModMobEffects.FRESH_BANDAGE_EFFECT_NAME;

import net.minecraft.Util;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import ru.ancndz.bandagez.BandagezMod;

public class FreshBandageMobEffect extends MobEffect implements EffectPriority {

	public static final int TICK_RATE = 40;

	public static final float CHANCE_TO_BLEED = 0.2F;

    private String descriptionId;

    public FreshBandageMobEffect(MobEffectCategory category, int color) {
		super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int food) {
        if (entity.isSprinting() && entity.getRandom().nextFloat() < CHANCE_TO_BLEED) {
			entity.addEffect(new MobEffectInstance(ModMobEffects.BLEEDING.get(), 400, 0, false, false, true));
			entity.removeEffect(ModMobEffects.FRESH_BANDAGE.get());
        }
        if (entity.level().isClientSide) {
            addParticles(entity, new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.COBWEB.defaultBlockState()));
        }
    }

    @Override
    public boolean isDurationEffectTick(int tick, int amplifier) {
        int j = TICK_RATE >> amplifier;
		return j == 0 || tick % j == 0;
    }

    @Override
    protected String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("effect",
                ResourceLocation.tryBuild(BandagezMod.MODID, FRESH_BANDAGE_EFFECT_NAME));
        }
        return this.descriptionId;
    }

    @Override
    public EffectPriorities getPriority() {
        return EffectPriorities.FRESH_BANDAGE;
    }
}
