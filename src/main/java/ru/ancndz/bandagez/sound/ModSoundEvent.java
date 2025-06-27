package ru.ancndz.bandagez.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.ancndz.bandagez.mod.BandagezMod;

public class ModSoundEvent {

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, BandagezMod.MODID);

    public static final RegistryObject<SoundEvent> SMALL_BANDAGE_USE = SOUNDS.register("item.band.small_bandage_inuse",
            () -> new SoundEvent(new ResourceLocation(BandagezMod.MODID, "item.band.small_bandage_inuse")));

    public static final RegistryObject<SoundEvent> MEDIUM_BANDAGE_USE =
            SOUNDS.register("item.band.medium_bandage_inuse",
                    () -> new SoundEvent(new ResourceLocation(BandagezMod.MODID, "item.band.medium_bandage_inuse")));

    public static final RegistryObject<SoundEvent> BANDAGE_USE_START = SOUNDS.register("item.band.bandage_use_start",
            () -> new SoundEvent(new ResourceLocation(BandagezMod.MODID, "item.band.bandage_use_start")));

    public static final RegistryObject<SoundEvent> BANDAGE_USE_MID = SOUNDS.register("item.band.bandage_use_mid",
            () -> new SoundEvent(new ResourceLocation(BandagezMod.MODID, "item.band.bandage_use_mid")));

    public static final RegistryObject<SoundEvent> BANDAGE_USE_END = SOUNDS.register("item.band.bandage_use_end",
            () -> new SoundEvent(new ResourceLocation(BandagezMod.MODID, "item.band.bandage_use_end")));

    private ModSoundEvent() {
    }
}
