package ru.ancndz.bandagez;

import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.ancndz.bandagez.effect.ModMobEffects;
import ru.ancndz.bandagez.item.GrassDropModifier;
import ru.ancndz.bandagez.item.ModItems;
import ru.ancndz.bandagez.mod.BandagezMod;
import ru.ancndz.bandagez.mod.BandagezModConfig;
import ru.ancndz.bandagez.sound.ModSoundEvents;

@Mod(BandagezMod.MODID)
public class BandagezForge {

    public BandagezForge(FMLJavaModLoadingContext context) {
        final BusGroup modEventBus = context.getModBusGroup();
        BandagezModConfig.init();
        GrassDropModifier.LOOT_MODIFIERS.register();
        ModSoundEvents.SOUNDS.register();
        ModItems.ITEMS.register();
        ModItems.CREATIVE_MODE_TABS.register();
        ModMobEffects.EFFECTS.register();
        GrassDropModifier.LOOT_MODIFIERS.register();
    }
}
