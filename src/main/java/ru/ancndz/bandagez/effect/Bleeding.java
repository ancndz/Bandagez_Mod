package ru.ancndz.bandagez.effect;

import net.minecraft.Util;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.ancndz.bandagez.mod.BandagezMod;
import ru.ancndz.bandagez.mod.BandagezModConfig;

import javax.annotation.Nullable;

public class Bleeding extends MobEffect {

    public static final int TICK_RATE = 40;

    private final boolean hard;

    @Nullable
    private String descriptionId;

    public Bleeding(boolean hard, MobEffectCategory category, int color) {
        super(category, color, ParticleTypes.FALLING_LAVA);
        this.hard = hard;
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int food) {
        entity.hurtServer(level, entity.damageSources().genericKill(), hard ? 1.5F : 1.0F);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
        int j = TICK_RATE >> amplifier;
        return j == 0 || tick % j == 0;
    }

    @Override
    public ParticleOptions createParticleOptions(MobEffectInstance p_332465_) {
        if (BandagezModConfig.CLIENT.showParticles.get()) {
            return super.createParticleOptions(p_332465_);
        } else {
            return () -> ParticleTypes.EFFECT;
        }
    }

    @Override
    protected @NotNull String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            this.descriptionId = Util.makeDescriptionId("effect",
                    ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, hard ? "hard_bleed" : "bleed"));
        }
        return this.descriptionId;
    }

}
