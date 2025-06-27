package ru.ancndz.bandagez.effect;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.ancndz.bandagez.mod.BandagezMod;

public class ModEffects {

    public static final DeferredRegister<Effect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.POTIONS, BandagezMod.MODID);

    public static final String BLEEDING_EFFECT_NAME = "bleeding";

    public static final String HARD_BLEEDING_EFFECT_NAME = "hard_bleeding";

    public static final String LEG_FRACTURE_EFFECT_NAME = "leg_fractured";

    public static final String ARM_MAIN_FRACTURE_EFFECT_NAME = "arm_main_fractured";

    public static final String ARM_FRACTURE_EFFECT_NAME = "arm_fractured";

    public static final String FRESH_BANDAGE_EFFECT_NAME = "fresh_bandage";

    public static final RegistryObject<Effect> BLEEDING =
            EFFECTS.register(BLEEDING_EFFECT_NAME, () -> new BleedingMobEffect(false, EffectType.HARMFUL, 13458603));

    public static final RegistryObject<Effect> HARD_BLEEDING = EFFECTS.register(HARD_BLEEDING_EFFECT_NAME,
            () -> new BleedingMobEffect(true, EffectType.HARMFUL, 13458603));

    public static final RegistryObject<Effect> BONE_FRACTURE_LEG = EFFECTS.register(LEG_FRACTURE_EFFECT_NAME,
            () -> new BoneFracturedMobEffect(BoneFracturedMobEffect.BodyPart.LEG, EffectType.HARMFUL, 13458603));

    public static final RegistryObject<Effect> BONE_FRACTURE_ARM_MAIN = EFFECTS.register(ARM_MAIN_FRACTURE_EFFECT_NAME,
            () -> new BoneFracturedMobEffect(BoneFracturedMobEffect.BodyPart.ARM_MAIN, EffectType.HARMFUL, 13458603));

    public static final RegistryObject<Effect> BONE_FRACTURE_ARM = EFFECTS.register(ARM_FRACTURE_EFFECT_NAME,
            () -> new BoneFracturedMobEffect(BoneFracturedMobEffect.BodyPart.ARM, EffectType.HARMFUL, 13458603));

    public static final RegistryObject<Effect> FRESH_BANDAGE =
            EFFECTS.register(FRESH_BANDAGE_EFFECT_NAME, () -> new FreshBandageMobEffect(EffectType.NEUTRAL, 13458603));

    private ModEffects() {
    }

}
