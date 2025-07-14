package ru.ancndz.bandagez;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
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
        final Pair<ClientModConfiguration<ForgeConfigSpec.ConfigValue<?>>, ForgeConfigSpec> specPair =
                new ForgeConfigSpec.Builder()
                        .configure(builder -> new ClientModConfiguration<>() {
                            @Override
                            protected <V extends Comparable<? super V>> V getValue(String path) {
                                return ForgeConfigConverter.<V>toValue().apply(getValueRaw(path));
                            }

                            @Override
                            protected <V extends Comparable<? super V>> void putValue(ConfigEntry<V> configEntry) {
                                getValues().put(configEntry.getPath(), ForgeConfigConverter.<V>toConfigImplValue(builder).apply(configEntry));
                            }
                        });
        ModConfiguration.setClientConfig(specPair.getLeft());
        return specPair.getRight();
    }

    private static ForgeConfigSpec buildServerForgeConfigSpec() {
        final Pair<ServerModConfiguration<ForgeConfigSpec.ConfigValue<?>>, ForgeConfigSpec> specPair =
                new ForgeConfigSpec.Builder()
                        .configure(builder -> new ServerModConfiguration<>() {
                            @Override
                            protected <V extends Comparable<? super V>> V getValue(String path) {
                                return ForgeConfigConverter.<V>toValue().apply(getValueRaw(path));
                            }

                            @Override
                            protected <V extends Comparable<? super V>> void putValue(ConfigEntry<V> configEntry) {
                                getValues().put(configEntry.getPath(), ForgeConfigConverter.<V>toConfigImplValue(builder).apply(configEntry));
                            }
                        });
        ModConfiguration.setServerConfig(specPair.getLeft());
        return specPair.getRight();
    }

}

