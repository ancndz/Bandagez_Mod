package ru.ancndz.bandagez.effect;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
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
                    "7107DE5E-7CE8-4030-940E-514C1F160890",
                    -0.45F,
                    AttributeModifier.Operation.MULTIPLY_TOTAL);
        }

    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int food) {
        if (bodyPart == BodyPart.LEG && entity.isSprinting()) {
            entity.hurt(entity.damageSources().genericKill(), 0.5F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int tick, int amplifier) {
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
