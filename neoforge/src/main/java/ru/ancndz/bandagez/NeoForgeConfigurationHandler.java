package ru.ancndz.bandagez;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class NeoForgeConfigurationHandler {

    public static void init(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, buildForgeConfigSpec());
    }

    private static ModConfigSpec buildForgeConfigSpec() {
        final Pair<IModConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(builder -> {
            final IModConfig configuration = new IModConfig() {
                private final ModConfigSpec.BooleanValue showParticles =
                        builder.comment("Enable or disable particles for bandages")
                                .translation("bandagez.config.showParticles")
                                .define("showParticles", true);

                @Override
                public Boolean showParticles() {
                    return showParticles.get();
                }
            };
            return configuration;
        });
        ConfigHolder.setConfig(specPair.getLeft());
        return specPair.getRight();
    }
}
