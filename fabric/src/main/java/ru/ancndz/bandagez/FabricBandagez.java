package ru.ancndz.bandagez;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class FabricBandagez implements ModInitializer {

    @Override
    public void onInitialize() {
        BandagezMod.init();
        FabricEventHandler.init();
        FabricConfigurationHandler.init(FabricLoader.getInstance());
        BandagezMod.onLoadComplete();
    }

}
