package ru.ancndz.bandagez.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.RegistryObject;
import ru.ancndz.bandagez.mod.BandagezMod;

public class Effects {

	public static final RegistryObject<MobEffect> BLEEDING = BandagezMod.EFFECTS.register("bleeding",
			() -> new Bleeding(false, MobEffectCategory.HARMFUL, 13458603));

	public static final RegistryObject<MobEffect> HARD_BLEEDING = BandagezMod.EFFECTS.register("hard_bleeding",
			() -> new Bleeding(true, MobEffectCategory.HARMFUL, 13458603));

	public static final RegistryObject<MobEffect> FRESH_BANDAGE = BandagezMod.EFFECTS.register("fresh_bandage",
			() -> new FreshBandage(MobEffectCategory.NEUTRAL, 13458603));

	public static void init() {
		BLEEDING.hashCode();
		HARD_BLEEDING.hashCode();
		FRESH_BANDAGE.hashCode();
	}

	private Effects() {
	}

}
