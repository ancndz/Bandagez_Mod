package ru.ancndz.bandagez;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ancndz.bandagez.effect.ModMobEffects;
import ru.ancndz.bandagez.item.ModItems;

public class BandagezMod {

    public static final String MODID = "bandagez";

    public static final Logger LOG = LoggerFactory.getLogger(MODID);

    public static void init() {
        ModMobEffects.init();
        ModItems.init();
    }

    public static void onLoadComplete() {
        BandagezMod.LOG.info("showParticles: {}", ConfigHolder.getConfig().showParticles());
    }

}