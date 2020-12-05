package ru.ancndz.bandmod.event;

import ru.ancndz.bandmod.mod.MainClassMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import squeek.applecore.api.hunger.HealthRegenEvent.AllowRegen;
import squeek.applecore.api.hunger.HealthRegenEvent.AllowSaturatedRegen;

@Mod.EventBusSubscriber(modid = MainClassMod.MODID)
public class EventHealingEating {

	@SubscribeEvent
	public static void Healing(AllowRegen event) {
		if (event.player.getHealth() < 16)
		event.setResult(Result.DENY);
		else event.setResult(Result.DEFAULT);
	}
	
	
	@SubscribeEvent
	public static void SaturationHealing(AllowSaturatedRegen event) {
		if (event.player.getHealth() < 16)
			event.setResult(Result.DENY);
			else event.setResult(Result.DEFAULT);
	}
/*
	@SubscribeEvent
	public static void Starve(GetStarveTickPeriod event) {
		event.starveTickPeriod = 320;
	}
	
	
	@SubscribeEvent
	public static void Hunger(GetMaxExhaustion event) {
		event.maxExhaustionLevel = 5f;
	} */
}
