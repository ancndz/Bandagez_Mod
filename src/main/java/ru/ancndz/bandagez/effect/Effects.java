package ru.ancndz.bandagez.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.RegistryObject;
import ru.ancndz.bandagez.mod.BandagezMod;

public class Effects {

	public static final RegistryObject<MobEffect> BLEEDING = BandagezMod.EFFECTS.register("bleeding",
            () -> new BleedingMobEffect(false, MobEffectCategory.HARMFUL, 13458603));

	public static final RegistryObject<MobEffect> HARD_BLEEDING = BandagezMod.EFFECTS.register("hard_bleeding",
            () -> new BleedingMobEffect(true, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_BREAK_LEG = BandagezMod.EFFECTS.register("leg_broken",
            () -> new BoneBrokenMobEffect(BoneBrokenMobEffect.BodyPart.LEG, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_BREAK_ARM_MAIN = BandagezMod.EFFECTS.register("arm_main_broken",
            () -> new BoneBrokenMobEffect(BoneBrokenMobEffect.BodyPart.ARM_MAIN, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> BONE_BREAK_ARM = BandagezMod.EFFECTS.register("arm_broken",
            () -> new BoneBrokenMobEffect(BoneBrokenMobEffect.BodyPart.ARM, MobEffectCategory.HARMFUL, 13458603));

	public static final RegistryObject<MobEffect> FRESH_BANDAGE = BandagezMod.EFFECTS.register("fresh_bandage",
			() -> new FreshBandage(MobEffectCategory.NEUTRAL, 13458603));

	public static void init() {
		BLEEDING.hashCode();
		HARD_BLEEDING.hashCode();
		FRESH_BANDAGE.hashCode();
        BONE_BREAK_LEG.hashCode();
        BONE_BREAK_ARM_MAIN.hashCode();
        BONE_BREAK_ARM.hashCode();
	}

	private Effects() {
	}

}
