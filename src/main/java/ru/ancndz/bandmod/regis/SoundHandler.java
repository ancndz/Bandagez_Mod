package ru.ancndz.bandmod.regis;


import ru.ancndz.bandmod.mod.MainClassMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundHandler {

	public static SoundEvent SMALL_BANDAGE_USE, MEDIUM_BANDAGE_USE, LARGE_BANDAGE_USE, BANDAGE_AFTERUSE;
	
	public static void registerSounds() {
		SMALL_BANDAGE_USE = registerSound("item.band.small_bandage_inuse");
		MEDIUM_BANDAGE_USE = registerSound("item.band.medium_bandage_inuse");
		LARGE_BANDAGE_USE = registerSound("item.band.large_bandage_inuse");
		BANDAGE_AFTERUSE = registerSound("item.band.bandage_afteruse");
	}
	
	public static SoundEvent registerSound(String name) {
		
		ResourceLocation location = new ResourceLocation(MainClassMod.MODID, name);
		SoundEvent event = new SoundEvent(location);
				event.setRegistryName(name);
		
		ForgeRegistries.SOUND_EVENTS.register(event);
		return event;
	}
	
}
