package ru.ancndz.bandagez.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import ru.ancndz.bandagez.BandagezMod;

@EventBusSubscriber(modid = BandagezMod.MODID)
public class NeoForgeEventHandler {

    @SubscribeEvent
    public static void onPlayerFall(LivingFallEvent event) {
        event.setDamageMultiplier(
            event.getDamageMultiplier() + BoneFracturedEventHandler.onPlayerFall(event.getEntity()));
    }

    @SubscribeEvent
    public static void onPlayerTakesFallDamage(LivingDamageEvent.Post event) {
        BoneFracturedEventHandler.onPlayerTakesFallDamage(event.getSource(), event.getEntity(), event.getNewDamage());
    }

    @SubscribeEvent
    public static void onPlayerTakesDamage(LivingDamageEvent.Pre event) {
        BleedingEventHandler.onPlayerTakesDamage(event.getSource(), event.getEntity());
        BoneFracturedEventHandler.onPlayerTakesDamage(event.getSource(), event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
        event.setCanceled(BoneFracturedEventHandler.onPlayerUse(event.getHand(), event.getEntity()));
    }

    @SubscribeEvent
    public static void onPlayerUse(PlayerInteractEvent.RightClickItem event) {
        event.setCanceled(BoneFracturedEventHandler.onPlayerUse(event.getHand(), event.getEntity()));
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        WorldEventHandler.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        WorldEventHandler.onItemTooltipEvent(event.getItemStack(), event.getToolTip());
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        WorldEventHandler.onPlayerLoggedIn(event.getEntity());
    }

}
