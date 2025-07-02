package ru.ancndz.bandagez.event;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;

public class FabricEventHandler {

    public static void init() {
        UseItemCallback.EVENT
                .register((player, world, hand) -> BoneFracturedEventHandler.onPlayerUseWithResult(hand, player));
        UseBlockCallback.EVENT.register(
                (player, world, hand, hitResult) -> BoneFracturedEventHandler.onPlayerUseWithResult(hand, player));
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> BoneFracturedEventHandler
                .onPlayerUseWithResult(hand, player));

        ServerLivingEntityEvents.AFTER_DAMAGE.register((livingEntity, damageSource, v, v1, b) -> {
            BleedingEventHandler.onPlayerTakesDamage(damageSource, livingEntity);
            BoneFracturedEventHandler.onPlayerTakesDamage(damageSource, livingEntity);
        });

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((livingEntity, damageSource, v) -> {
            BoneFracturedEventHandler.onPlayerTakesFallDamage(damageSource, livingEntity, v);
            return true;
        });

        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipFlag, list) -> WorldEventHandler
                .onItemTooltipEvent(itemStack, list));

        ServerLifecycleEvents.SERVER_STARTING.register(WorldEventHandler::onServerStarting);
    }

}
