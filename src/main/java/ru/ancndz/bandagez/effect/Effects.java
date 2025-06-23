package ru.ancndz.bandagez.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.RegistryObject;
import ru.ancndz.bandagez.mod.BandagezMod;

public class Effects {

	public static void init() {}

    public static final String BLEEDING_EFFECT_NAME = "bleeding";

    public static final String HARD_BLEEDING_EFFECT_NAME = "hard_bleeding";

    public static final String LEG_BROKEN_EFFECT_NAME = "leg_broken";

    public static final String ARM_MAIN_BROKEN_EFFECT_NAME = "arm_main_broken";

    public static final String ARM_BROKEN_EFFECT_NAME = "arm_broken";

    public static final String FRESH_BANDAGE_EFFECT_NAME = "fresh_bandage";

    public static final RegistryObject<MobEffect> BLEEDING = BandagezMod.EFFECTS.register(BLEEDING_EFFECT_NAME,
            () -> new BleedingMobEffect(false, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> HARD_BLEEDING =
            BandagezMod.EFFECTS.register(HARD_BLEEDING_EFFECT_NAME,
            () -> new BleedingMobEffect(true, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_BREAK_LEG = BandagezMod.EFFECTS.register(LEG_BROKEN_EFFECT_NAME,
            () -> new BoneBrokenMobEffect(BoneBrokenMobEffect.BodyPart.LEG, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_BREAK_ARM_MAIN = BandagezMod.EFFECTS.register(
            ARM_MAIN_BROKEN_EFFECT_NAME,
            () -> new BoneBrokenMobEffect(BoneBrokenMobEffect.BodyPart.ARM_MAIN, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_BREAK_ARM = BandagezMod.EFFECTS.register(ARM_BROKEN_EFFECT_NAME,
            () -> new BoneBrokenMobEffect(BoneBrokenMobEffect.BodyPart.ARM, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> FRESH_BANDAGE = BandagezMod.EFFECTS
            .register(FRESH_BANDAGE_EFFECT_NAME,
			() -> new FreshBandage(MobEffectCategory.NEUTRAL, 13458603));

	private Effects() {
	}

}
