package ru.ancndz.bandagez;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.ancndz.bandagez.config.ClientModConfiguration;
import ru.ancndz.bandagez.config.ConfigEntry;
import ru.ancndz.bandagez.config.ModConfiguration;
import ru.ancndz.bandagez.config.ServerModConfiguration;

public class ForgeConfigurationHandler {

    public static void init(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.CLIENT, buildClientForgeConfigSpec());
        context.registerConfig(ModConfig.Type.SERVER, buildServerForgeConfigSpec());
    }

    private static ForgeConfigSpec buildClientForgeConfigSpec() {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        final var config = new ClientModConfiguration<ForgeConfigSpec.ConfigValue<?>>() {


            @Override
            protected <V extends Comparable<? super V>> V getValue(String path) {
                return ForgeConfigConverter.<V>toValue().apply(getValueRaw(path));
            }

            @Override
            protected <V extends Comparable<? super V>> void putValue(ConfigEntry<V> configEntry) {
                getValues().put(configEntry.getPath(), ForgeConfigConverter.<V>toConfigImplValue(builder).apply(configEntry));
            }
        };

        ModConfiguration.setClientConfig(config);
        return builder.build();
    }

    private static ForgeConfigSpec buildServerForgeConfigSpec() {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        final var config = new ServerModConfiguration<ForgeConfigSpec.ConfigValue<?>>() {


            @Override
            protected <V extends Comparable<? super V>> V getValue(String path) {
                return ForgeConfigConverter.<V>toValue().apply(getValueRaw(path));
            }

            @Override
            protected <V extends Comparable<? super V>> void putValue(ConfigEntry<V> configEntry) {
                getValues().put(configEntry.getPath(), ForgeConfigConverter.<V>toConfigImplValue(builder).apply(configEntry));
            }
        };

        ModConfiguration.setServerConfig(config);
        return builder.build();
    }


}

