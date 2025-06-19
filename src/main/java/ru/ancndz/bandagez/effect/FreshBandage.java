package ru.ancndz.bandagez.effect;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.ancndz.bandagez.mod.BandagezMod;

import javax.annotation.Nullable;

public class FreshBandage extends MobEffect {

	public static final int TICK_RATE = 40;

	public static final float CHANCE_TO_BLEED = 0.2F;

    @Nullable
    private String descriptionId;

    public FreshBandage(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int food) {
        if (entity.isSprinting() && entity.getRandom().nextFloat() < CHANCE_TO_BLEED) {
			entity.addEffect(new MobEffectInstance(Effects.BLEEDING.getHolder().orElseThrow(), MobEffectInstance.INFINITE_DURATION));
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
                    ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "fresh_bandage"));
        }
        return this.descriptionId;
    }
}
