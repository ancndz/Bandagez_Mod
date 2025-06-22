package ru.ancndz.bandagez.effect;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;
import ru.ancndz.bandagez.mod.BandagezMod;

import javax.annotation.Nullable;

public class BoneBrokenMobEffect extends MobEffect {

    public static final int DAMAGE_INTERVAL = 80;

    private final BodyPart bodyPart;

    @Nullable
    private String descriptionId;

    protected BoneBrokenMobEffect(BodyPart bodyPart, MobEffectCategory category, int color) {
        super(category, color);
        this.bodyPart = bodyPart;

        if (bodyPart == BodyPart.LEG) {
            this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    ResourceLocation.withDefaultNamespace("effect.slowness"),
                    -0.45F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        }
    }

    @Override
    public boolean applyEffectTick(@NotNull ServerLevel level, @NotNull LivingEntity entity, int food) {
        if (bodyPart == BodyPart.LEG && entity.isSprinting()) {
            entity.hurtServer(level, entity.damageSources().genericKill(), 0.5F);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tick, int amplifier) {
        int j = DAMAGE_INTERVAL >> amplifier;
        return j == 0 || tick % j == 0;
    }

    @Override
    protected @NotNull String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            final String path = switch (bodyPart) {
                case ARM -> "arm_broken";
                case ARM_MAIN -> "arm_main_broken";
                default -> "leg_broken";
            };
            this.descriptionId =
                    Util.makeDescriptionId("effect", ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, path));
        }
        return this.descriptionId;
    }

    public enum BodyPart {
        LEG, ARM, ARM_MAIN
    }
}
