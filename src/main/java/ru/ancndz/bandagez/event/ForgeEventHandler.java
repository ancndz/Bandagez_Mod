package ru.ancndz.bandagez.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.ancndz.bandagez.item.SupplyCustomTooltip;
import ru.ancndz.bandagez.item.Typed;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.List;

@Mod.EventBusSubscriber(modid = BandagezMod.MODID)
public class ForgeEventHandler {

    @SubscribeEvent
    static void onServerStarting(ServerStartingEvent event) {
        event.getServer().getGameRules().getRule(GameRules.RULE_NATURAL_REGENERATION).set(false, event.getServer());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    static void onItemTooltipEvent(ItemTooltipEvent event) {
        final Item item = event.getItemStack().getItem();
        final List<Component> component = event.getToolTip();
        final Object itemType = item instanceof Typed<?> typed ? typed.getType() : item;
        if (itemType instanceof SupplyCustomTooltip supplyCustomTooltip) {
            supplyCustomTooltip.addCustomTooltip(component);
        }
    }

}
