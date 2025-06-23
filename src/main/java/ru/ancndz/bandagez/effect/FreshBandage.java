package ru.ancndz.bandagez.effect;

import static ru.ancndz.bandagez.effect.Effects.FRESH_BANDAGE_EFFECT_NAME;

import net.minecraft.Util;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import ru.ancndz.bandagez.mod.BandagezMod;

import javax.annotation.Nullable;

public class FreshBandage extends MobEffect implements EffectPriority {

	public static final int TICK_RATE = 40;

	public static final float CHANCE_TO_BLEED = 0.2F;

    @Nullable
    private String descriptionId;

    public FreshBandage(MobEffectCategory category, int color) {
		super(category, color, new BlockParticleOption(ParticleTypes.FALLING_DUST, Blocks.COBWEB.defaultBlockState()));
    }

    @Override
	public boolean applyEffectTick(@NotNull ServerLevel level, @NotNull LivingEntity entity, int food) {
        if (entity.isSprinting() && entity.getRandom().nextFloat() < CHANCE_TO_BLEED) {
			entity.addEffect(new MobEffectInstance(Effects.BLEEDING.getHolder().orElseThrow(), 400));
			entity.removeEffect(Effects.FRESH_BANDAGE.getHolder().orElseThrow());
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
        int j = TICK_RATE >> amplifier;
        return j == 0 || tick % j == 0;
    }

    @Override
    protected @NotNull String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("effect",
                    ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, FRESH_BANDAGE_EFFECT_NAME));
        }
        return this.descriptionId;
    }

    @Override
    public EffectPriorities getPriority() {
        return EffectPriorities.FRESH_BANDAGE;
    }
}
