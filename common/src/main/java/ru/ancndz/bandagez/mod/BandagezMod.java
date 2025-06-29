package ru.ancndz.bandagez.mod;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BandagezMod {

    public static final String MODID = "bandagez";

    public static final Logger LOG = LoggerFactory.getLogger(MODID);

    public static ResourceLocation location(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

}