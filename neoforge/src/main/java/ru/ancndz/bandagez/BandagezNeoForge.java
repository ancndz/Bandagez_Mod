package ru.ancndz.bandagez;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod(BandagezMod.MODID)
public class BandagezNeoForge {

    public BandagezNeoForge(IEventBus modEventBus, Dist dist, ModContainer container) {
        BandagezMod.init();
        NeoForgeConfigurationHandler.init(container);
        GrassDropModifier.LOOT_MODIFIERS.register(GrassDropModifier.GRASS_DROP_MODIFIER,
                GrassDropModifier.CODEC_SUPPLIER);
        GrassDropModifier.LOOT_MODIFIERS.register(modEventBus);

        modEventBus.addListener(this::onLoadComplete);
    }

    private void onLoadComplete(final FMLLoadCompleteEvent event) {
        BandagezMod.onLoadComplete();
    }

}
