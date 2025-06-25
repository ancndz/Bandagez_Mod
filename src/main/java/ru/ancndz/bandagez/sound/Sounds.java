package ru.ancndz.bandagez.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import ru.ancndz.bandagez.mod.BandagezMod;

public class Sounds {

    public static void init() {
    }
	public static final RegistryObject<SoundEvent> SMALL_BANDAGE_USE = BandagezMod.SOUNDS
			.register("item.band.small_bandage_inuse", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.small_bandage_inuse")));

	public static final RegistryObject<SoundEvent> MEDIUM_BANDAGE_USE = BandagezMod.SOUNDS
			.register("item.band.medium_bandage_inuse", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.medium_bandage_inuse")));

	public static final RegistryObject<SoundEvent> BANDAGE_USE_START = BandagezMod.SOUNDS
			.register("item.band.bandage_use_start", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.bandage_use_start")));

	public static final RegistryObject<SoundEvent> BANDAGE_USE_MID = BandagezMod.SOUNDS
			.register("item.band.bandage_use_mid", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.bandage_use_mid")));

	public static final RegistryObject<SoundEvent> BANDAGE_USE_END = BandagezMod.SOUNDS
			.register("item.band.bandage_use_end", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.bandage_use_end")));

	private Sounds() {
	}
}
