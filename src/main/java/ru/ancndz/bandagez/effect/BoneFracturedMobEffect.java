package ru.ancndz.bandagez.effect;

import static ru.ancndz.bandagez.effect.ModEffects.ARM_FRACTURE_EFFECT_NAME;
import static ru.ancndz.bandagez.effect.ModEffects.ARM_MAIN_FRACTURE_EFFECT_NAME;
import static ru.ancndz.bandagez.effect.ModEffects.LEG_FRACTURE_EFFECT_NAME;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import ru.ancndz.bandagez.mod.BandagezMod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BoneFracturedMobEffect extends Effect implements EffectPriority {

    public static final String BROKEN_BONE_ATTACK_DAMAGE = "2ec606e1-fbe7-4956-97ae-a76fb5018fbc";

    public static final String SLOWNESS = "7107DE5E-7CE8-4030-940E-514C1F160890";

    public static final int DAMAGE_INTERVAL = 80;

    private final BodyPart bodyPart;

    @Nullable
    private String descriptionId;

    protected BoneFracturedMobEffect(BodyPart bodyPart, EffectType category, int color) {
        super(category, color);
        this.bodyPart = bodyPart;

        switch (bodyPart) {
            case LEG: {
                this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    SLOWNESS,
                    -0.45F,
                    AttributeModifier.Operation.MULTIPLY_TOTAL);
                break;
            }

            case ARM_MAIN: {
                this.addAttributeModifier(Attributes.ATTACK_DAMAGE,
                    BROKEN_BONE_ATTACK_DAMAGE,
                    -0.4F,
                    AttributeModifier.Operation.MULTIPLY_BASE);
                break;
            }
        }
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity entity, int food) {
        if (bodyPart == BodyPart.LEG && entity.isSprinting()) {
            entity.hurt(DamageSource.MAGIC, 0.5F);
        }
    }

    @Override
    public boolean isDurationEffectTick(int tick, int amplifier) {
        int j = DAMAGE_INTERVAL >> amplifier;
        return j == 0 || tick % j == 0;
    }

    @Override
    protected @Nonnull String getOrCreateDescriptionId() {
        if (this.descriptionId == null) {
            final String path;
            switch (bodyPart) {
                case ARM:
                    path = ARM_FRACTURE_EFFECT_NAME;
                    break;
                case ARM_MAIN:
                    path = ARM_MAIN_FRACTURE_EFFECT_NAME;
                    break;
                default:
                    path = LEG_FRACTURE_EFFECT_NAME;
                    break;
            }

            this.descriptionId =
                    Util.makeDescriptionId("effect", new ResourceLocation(BandagezMod.MODID, path));
        }
        return this.descriptionId;
    }

    @Override
    public EffectPriorities getPriority() {
        switch (bodyPart) {
            case ARM:
                return EffectPriorities.ARM_FRACTURE;
            case ARM_MAIN:
                return EffectPriorities.MAIN_ARM_FRACTURE;
            default:
                return EffectPriorities.LEG_FRACTURE;
        }
    }

    public enum BodyPart {
        LEG, ARM, ARM_MAIN
    }

}
