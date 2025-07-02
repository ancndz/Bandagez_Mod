package ru.ancndz.bandagez.effect;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import ru.ancndz.bandagez.BandagezMod;
import ru.ancndz.bandagez.registration.RegistrationProvider;
import ru.ancndz.bandagez.registration.RegistryObject;

public class ModMobEffects {

    /**
     * The provider for items
     */
    public static final RegistrationProvider<MobEffect> EFFECTS =
            RegistrationProvider.get(BuiltInRegistries.MOB_EFFECT, BandagezMod.MODID);

    public static final String BLEEDING_EFFECT_NAME = "bleeding";

    public static final String HARD_BLEEDING_EFFECT_NAME = "hard_bleeding";

    public static final String LEG_FRACTURE_EFFECT_NAME = "leg_fractured";

    public static final String ARM_MAIN_FRACTURE_EFFECT_NAME = "arm_main_fractured";

    public static final String ARM_FRACTURE_EFFECT_NAME = "arm_fractured";

    public static final String FRESH_BANDAGE_EFFECT_NAME = "fresh_bandage";

    public static final RegistryObject<MobEffect> BLEEDING = EFFECTS.register(BLEEDING_EFFECT_NAME,
            () -> new BleedingMobEffect(false, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> HARD_BLEEDING =
            EFFECTS.register(HARD_BLEEDING_EFFECT_NAME,
            () -> new BleedingMobEffect(true, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_FRACTURE_LEG = EFFECTS.register(
            LEG_FRACTURE_EFFECT_NAME,
            () -> new BoneFracturedMobEffect(BoneFracturedMobEffect.BodyPart.LEG, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_FRACTURE_ARM_MAIN =
            EFFECTS.register(ARM_MAIN_FRACTURE_EFFECT_NAME,
                    () -> new BoneFracturedMobEffect(BoneFracturedMobEffect.BodyPart.ARM_MAIN,
                            MobEffectCategory.HARMFUL,
                            13458603));

    public static final RegistryObject<MobEffect> BONE_FRACTURE_ARM = EFFECTS.register(
            ARM_FRACTURE_EFFECT_NAME,
            () -> new BoneFracturedMobEffect(BoneFracturedMobEffect.BodyPart.ARM, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> FRESH_BANDAGE = EFFECTS
            .register(FRESH_BANDAGE_EFFECT_NAME,
			() -> new FreshBandageMobEffect(MobEffectCategory.NEUTRAL, 13458603));

    private ModMobEffects() {
	}

}
