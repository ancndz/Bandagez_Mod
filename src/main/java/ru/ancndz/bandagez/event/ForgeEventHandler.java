package ru.ancndz.bandagez.event;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.ancndz.bandagez.item.Healing;
import ru.ancndz.bandagez.item.RemovingEffects;
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
    static void onItemTooltipEvent(ItemTooltipEvent event) {
        final Item item = event.getItemStack().getItem();
        final List<Component> component = event.getToolTip();
        if (!(item instanceof Typed<?> typed)) {
            return;
        }
        final var itemType = typed.getType();
        if (itemType instanceof Healing healing) {
            component.add(healing.getTooltipComponent());
        }
        if (itemType instanceof RemovingEffects removingEffectsType) {
            component.add(Component.translatable("bandagez.tooltip.removing_effects"));
            for (var effect : removingEffectsType.getRemovingEffects()) {
                component.add(Component.translatable(effect.getDescriptionId())
                        .withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }
    }

}
