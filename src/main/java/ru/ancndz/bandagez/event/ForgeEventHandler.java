package ru.ancndz.bandagez.event;

import net.minecraft.item.Item;
import net.minecraft.world.GameRules;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ancndz.bandagez.item.SupplyCustomTooltip;
import ru.ancndz.bandagez.item.Typed;
import ru.ancndz.bandagez.mod.BandagezMod;

@Mod.EventBusSubscriber(modid = BandagezMod.MODID)
public class ForgeEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ForgeEventHandler.class);

    @SubscribeEvent
    static void onServerStarting(final FMLServerStartedEvent event) {
        LOG.info("FMLServerStartingEvent: Setting RULE_NATURAL_REGENERATION = false!");
        event.getServer().getGameRules().getRule(GameRules.RULE_NATURAL_REGENERATION).set(false, event.getServer());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    static void onItemTooltipEvent(final ItemTooltipEvent event) {
        final Item item = event.getItemStack().getItem();
        final Object itemType;
        if (item instanceof Typed<?>) {
            itemType = ((Typed<?>) item).getType();
        } else {
            itemType = item;
        }
        if (itemType instanceof SupplyCustomTooltip) {
            ((SupplyCustomTooltip) itemType).addCustomTooltip(event.getToolTip());
        }
    }

}
