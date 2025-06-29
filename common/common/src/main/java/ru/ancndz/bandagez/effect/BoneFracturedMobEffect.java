package ru.ancndz.bandagez.effect;

import static ru.ancndz.bandagez.effect.ModMobEffects.ARM_FRACTURE_EFFECT_NAME;
import static ru.ancndz.bandagez.effect.ModMobEffects.ARM_MAIN_FRACTURE_EFFECT_NAME;
import static ru.ancndz.bandagez.effect.ModMobEffects.LEG_FRACTURE_EFFECT_NAME;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;
import ru.ancndz.bandagez.BandagezMod;

public class BoneFracturedMobEffect extends MobEffect implements EffectPriority {

    public static final String BROKEN_BONE_ATTACK_DAMAGE = "2ec606e1-fbe7-4956-97ae-a76fb5018fbc";

    public static final String SLOWNESS = "7107DE5E-7CE8-4030-940E-514C1F160890";

    public static final int DAMAGE_INTERVAL = 80;

    private final BodyPart bodyPart;

    private String descriptionId;

    protected BoneFracturedMobEffect(BodyPart bodyPart, MobEffectCategory category, int color) {
        super(category, color);
        this.bodyPart = bodyPart;

        switch (bodyPart) {
            case LEG -> this.addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    SLOWNESS,
                    -0.45F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

            case ARM_MAIN -> this.addAttributeModifier(Attributes.ATTACK_DAMAGE,
                    BROKEN_BONE_ATTACK_DAMAGE,
                    -0.4F,
                    AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        }
    }

    @Override
    public boolean applyEffectTick(@NotNull LivingEntity entity, int food) {
        if (bodyPart == BodyPart.LEG && entity.isSprinting()) {
            entity.hurt(entity.damageSources().magic(), 0.5F);
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
                case ARM -> ARM_FRACTURE_EFFECT_NAME;
                case ARM_MAIN -> ARM_MAIN_FRACTURE_EFFECT_NAME;
                default -> LEG_FRACTURE_EFFECT_NAME;
            };
            this.descriptionId =
                    Util.makeDescriptionId("effect", ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, path));
        }
        return this.descriptionId;
    }

    @Override
    public EffectPriorities getPriority() {
        return switch (bodyPart) {
            case LEG -> EffectPriorities.LEG_FRACTURE;
            case ARM -> EffectPriorities.ARM_FRACTURE;
            case ARM_MAIN -> EffectPriorities.MAIN_ARM_FRACTURE;
        };
    }

    public enum BodyPart {
        LEG, ARM, ARM_MAIN
    }

}
