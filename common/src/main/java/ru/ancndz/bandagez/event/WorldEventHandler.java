package ru.ancndz.bandagez.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import ru.ancndz.bandagez.item.SupplyCustomTooltip;
import ru.ancndz.bandagez.item.Typed;

import java.util.List;

public class WorldEventHandler {

    public static void onServerStarting(MinecraftServer server) {
        server.getGameRules().getRule(GameRules.RULE_NATURAL_REGENERATION).set(false, server);
    }

    public static void onItemTooltipEvent(ItemStack itemStack, List<Component> component) {
        final Object itemType = itemStack.getItem() instanceof Typed<?> typed ? typed.getType() : itemStack.getItem();
        if (itemType instanceof SupplyCustomTooltip supplyCustomTooltip) {
            supplyCustomTooltip.addCustomTooltip(component);
        }
    }

}
