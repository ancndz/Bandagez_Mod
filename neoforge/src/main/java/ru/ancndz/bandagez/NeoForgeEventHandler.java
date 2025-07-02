package ru.ancndz.bandagez;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import ru.ancndz.bandagez.event.BleedingEventHandler;
import ru.ancndz.bandagez.event.BoneFracturedEventHandler;
import ru.ancndz.bandagez.event.WorldEventHandler;

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
    public static boolean onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
        return BoneFracturedEventHandler.onPlayerUse(event.getHand(), event.getEntity());
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        WorldEventHandler.onServerStarting(event.getServer());
    }

    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        WorldEventHandler.onItemTooltipEvent(event.getItemStack(), event.getToolTip());
    }

}
