package ru.ancndz.bandagez.event;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.entity.LivingEntity;

public class FabricEventHandler {

    public static void init() {
        UseItemCallback.EVENT
            .register((player, world, hand) ->
                BoneFracturedEventHandler.onPlayerUseWithResultHolder(hand, player));
        UseBlockCallback.EVENT
            .register((player, world, hand, hitResult) ->
                BoneFracturedEventHandler.onPlayerUseWithResult(hand, player));

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((livingEntity, damageSource, v) -> {
            BleedingEventHandler.onPlayerTakesDamage(damageSource, livingEntity);
            BoneFracturedEventHandler.onPlayerTakesDamage(damageSource, livingEntity);
            BoneFracturedEventHandler.onPlayerTakesFallDamage(damageSource, livingEntity, v);
            return true;
        });

        ItemTooltipCallback.EVENT
            .register((itemStack, tooltipContext, tooltipFlag, list) ->
                WorldEventHandler.onItemTooltipEvent(itemStack, list));

        ServerEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof LivingEntity livingEntity) {
                WorldEventHandler.onPlayerLoggedIn(livingEntity);
            }
        });

        ServerLifecycleEvents.SERVER_STARTED.register(WorldEventHandler::onServerStarted);
    }

}
