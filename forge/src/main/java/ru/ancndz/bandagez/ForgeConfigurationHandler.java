package ru.ancndz.bandagez;

import java.util.function.Function;
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
                            final ForgeConfigConverter converter = new ForgeConfigConverter(builder);

                            @Override
                            protected <V extends Comparable<? super V>> V getValue(String path) {
                                return converter.<V>toValue().apply(getValueRaw(path));
                            }

                            @Override
                            protected <V extends Comparable<? super V>> void putValue(ConfigEntry<V> configEntry) {
                                getValues().put(configEntry.getPath(), converter.<V>toConfigImplValue().apply(configEntry));
                            }
                        });
        ModConfiguration.setClientConfig(specPair.getLeft());
        return specPair.getRight();
    }

    private static ForgeConfigSpec buildServerForgeConfigSpec() {
        final Pair<ServerModConfiguration<ForgeConfigSpec.ConfigValue<?>>, ForgeConfigSpec> specPair =
                new ForgeConfigSpec.Builder()
                        .configure(builder -> new ServerModConfiguration<>() {
                            final ForgeConfigConverter converter = new ForgeConfigConverter(builder);

                            @Override
                            protected <V extends Comparable<? super V>> V getValue(String path) {
                                return converter.<V>toValue().apply(getValueRaw(path));
                            }

                            @Override
                            protected <V extends Comparable<? super V>> void putValue(ConfigEntry<V> configEntry) {
                                getValues().put(configEntry.getPath(), converter.<V>toConfigImplValue().apply(configEntry));
                            }
                        });
        ModConfiguration.setServerConfig(specPair.getLeft());
        return specPair.getRight();
    }


    private static <V extends Comparable<? super V>> Function<ConfigEntry<V>, ForgeConfigSpec.ConfigValue<V>> getForgeValueConverter(ForgeConfigSpec.Builder builder) {
        return configEntry -> {
            var configValue = builder.comment(configEntry.getComment())
                    .translation(configEntry.getTranslation());
            if (configEntry.getRange() != null) {
                return configValue.defineInRange(configEntry.getPath(), configEntry.getValue(), configEntry.getRange().getMinimum(), configEntry.getRange().getMaximum(), configEntry.getValueClass());
            }
            return configValue.define(configEntry.getPath(), configEntry.getValue());
        };
    }
}

