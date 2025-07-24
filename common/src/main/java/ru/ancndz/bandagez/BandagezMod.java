package ru.ancndz.bandagez;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ancndz.bandagez.config.ModConfiguration;
import ru.ancndz.bandagez.effect.ModMobEffects;
import ru.ancndz.bandagez.effect.particle.ModParticles;
import ru.ancndz.bandagez.item.ModItems;
import ru.ancndz.bandagez.sound.ModSoundEvents;

public class BandagezMod {

    public static final String MODID = "bandagez";

    public static final Logger LOG = LoggerFactory.getLogger(MODID);

    public static void init() {
        ModParticles.init();
        ModMobEffects.init();
        ModSoundEvents.init();
        ModItems.init();
    }

    public static void onLoadComplete() {
        BandagezMod.LOG.info("showParticles: {}", ModConfiguration.getClientConfig().getShowParticles());
    }

}