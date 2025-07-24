package ru.ancndz.bandagez.sound;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import ru.ancndz.bandagez.BandagezMod;
import ru.ancndz.bandagez.registration.RegistrationProvider;
import ru.ancndz.bandagez.registration.RegistryObject;

public class ModSoundEvents {

    public static void init() {
    }/**
     * The provider for items
     */
    public static final RegistrationProvider<SoundEvent> SOUND_EVENTS =
            RegistrationProvider.get(BuiltInRegistries.SOUND_EVENT, BandagezMod.MODID);

    public static final RegistryObject<SoundEvent> SMALL_BANDAGE_USE = SOUND_EVENTS
			.register("item.band.small_bandage_inuse", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.tryBuild(BandagezMod.MODID, "item.band.small_bandage_inuse")));

    public static final RegistryObject<SoundEvent> MEDIUM_BANDAGE_USE = SOUND_EVENTS
			.register("item.band.medium_bandage_inuse", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.tryBuild(BandagezMod.MODID, "item.band.medium_bandage_inuse")));

    public static final RegistryObject<SoundEvent> BANDAGE_USE_START = SOUND_EVENTS
			.register("item.band.bandage_use_start", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.tryBuild(BandagezMod.MODID, "item.band.bandage_use_start")));

    public static final RegistryObject<SoundEvent> BANDAGE_USE_MID = SOUND_EVENTS
			.register("item.band.bandage_use_mid", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.tryBuild(BandagezMod.MODID, "item.band.bandage_use_mid")));

    public static final RegistryObject<SoundEvent> BANDAGE_USE_END = SOUND_EVENTS
			.register("item.band.bandage_use_end", () -> SoundEvent.createVariableRangeEvent(
					ResourceLocation.tryBuild(BandagezMod.MODID, "item.band.bandage_use_end")));

    private ModSoundEvents() {
    }
}
