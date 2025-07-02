package ru.ancndz.bandagez;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class ForgeConfigurationHandler {

    public static void init(FMLJavaModLoadingContext context) {

        context.registerConfig(ModConfig.Type.CLIENT, buildForgeConfigSpec());
    }

    private static ForgeConfigSpec buildForgeConfigSpec() {
        final Pair<IModConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
            final IModConfig configuration = new IModConfig() {

                private final ForgeConfigSpec.BooleanValue showParticles =
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
