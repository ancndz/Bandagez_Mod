package ru.ancndz.bandagez.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.RegistryObject;
import ru.ancndz.bandagez.mod.BandagezMod;

public class Effects {

	public static void init() {}

    public static final String BLEEDING_EFFECT_NAME = "bleeding";

    public static final String HARD_BLEEDING_EFFECT_NAME = "hard_bleeding";

    public static final String LEG_FRACTURE_EFFECT_NAME = "leg_fractured";

    public static final String ARM_MAIN_FRACTURE_EFFECT_NAME = "arm_main_fractured";

    public static final String ARM_FRACTURE_EFFECT_NAME = "arm_fractured";

    public static final String FRESH_BANDAGE_EFFECT_NAME = "fresh_bandage";

    public static final RegistryObject<MobEffect> BLEEDING = BandagezMod.EFFECTS.register(BLEEDING_EFFECT_NAME,
            () -> new BleedingMobEffect(false, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> HARD_BLEEDING =
            BandagezMod.EFFECTS.register(HARD_BLEEDING_EFFECT_NAME,
            () -> new BleedingMobEffect(true, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_FRACTURE_LEG = BandagezMod.EFFECTS.register(
            LEG_FRACTURE_EFFECT_NAME,
            () -> new BoneFracturedMobEffect(BoneFracturedMobEffect.BodyPart.LEG, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_FRACTURE_ARM_MAIN =
            BandagezMod.EFFECTS.register(ARM_MAIN_FRACTURE_EFFECT_NAME,
                    () -> new BoneFracturedMobEffect(BoneFracturedMobEffect.BodyPart.ARM_MAIN,
                            MobEffectCategory.HARMFUL,
                            13458603));

    public static final RegistryObject<MobEffect> BONE_FRACTURE_ARM = BandagezMod.EFFECTS.register(
            ARM_FRACTURE_EFFECT_NAME,
            () -> new BoneFracturedMobEffect(BoneFracturedMobEffect.BodyPart.ARM, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> FRESH_BANDAGE = BandagezMod.EFFECTS
            .register(FRESH_BANDAGE_EFFECT_NAME,
			() -> new FreshBandageMobEffect(MobEffectCategory.NEUTRAL, 13458603));

	private Effects() {
	}

}
