package ru.ancndz.bandagez.effect;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.ancndz.bandagez.mod.BandagezMod;

import javax.annotation.Nullable;

public class FreshBandage extends MobEffect {

    public static final int TICK_RATE = 10;

    public static final float CHANCE_TO_BLEED = 0.3F;

    @Nullable
    private String descriptionId;

    public FreshBandage(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int food) {
        if (entity.isSprinting() && entity.getRandom().nextFloat() < CHANCE_TO_BLEED) {
            entity.addEffect(new MobEffectInstance(BandagezMod.BLEEDING.get(), 100));
        }
    }

    @Override
    public boolean isDurationEffectTick(int tick, int amplifier) {
        int j = TICK_RATE >> amplifier;
        if (j > 0) {
            return tick % j == 0;
        } else {
            return true;
        }
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
