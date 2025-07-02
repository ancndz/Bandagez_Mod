package ru.ancndz.bandagez;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.ancndz.bandagez.loot.GrassDropModifier;

@Mod(BandagezMod.MODID)
public class BandagezForge {

    public BandagezForge(FMLJavaModLoadingContext context) {
        final IEventBus modEventBus = context.getModEventBus();
        BandagezMod.init();
        ForgeConfigurationHandler.init(context);
        GrassDropModifier.LOOT_MODIFIERS.register(GrassDropModifier.GRASS_DROP_MODIFIER,
                GrassDropModifier.CODEC_SUPPLIER);
        GrassDropModifier.LOOT_MODIFIERS.register(modEventBus);
    }

    @SubscribeEvent
    public void loadComplete(final FMLLoadCompleteEvent event) {
        BandagezMod.onLoadComplete();
    }

}
