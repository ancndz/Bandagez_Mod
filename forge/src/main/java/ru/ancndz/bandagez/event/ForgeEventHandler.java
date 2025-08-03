package ru.ancndz.bandagez.event;

import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.ancndz.bandagez.BandagezMod;

@Mod.EventBusSubscriber(modid = BandagezMod.MODID)
public class ForgeEventHandler {

    @SubscribeEvent
    public static void onPlayerFall(LivingFallEvent event) {
        event.setDamageMultiplier(
            event.getDamageMultiplier() + BoneFracturedEventHandler.onPlayerFall(event.getEntity()));
    }

    @SubscribeEvent
    public static void onPlayerTakesFallDamage(LivingDamageEvent event) {
        BoneFracturedEventHandler.onPlayerTakesFallDamage(event.getSource(), event.getEntity(), event.getAmount());
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
    public static void onServerStarted(ServerStartedEvent event) {
        WorldEventHandler.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        WorldEventHandler.onItemTooltipEvent(event.getItemStack(), event.getToolTip());
    }

}
