package ru.ancndz.bandagez.mod;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class BandagezModConfig {

    public static final class Client {

        public final ForgeConfigSpec.BooleanValue showParticles;

        Client(final ForgeConfigSpec.Builder builder) {
            showParticles = builder
                    .comment("Show particles on bleeding effects")
                    .translation("bandagez.config.showBleedingParticles")
                    .define("showParticles", true);
        }
    }

    public static final ForgeConfigSpec clientSpec;

    public static final Client CLIENT;

    static {
        final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(BandagezModConfig.Client::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
}
