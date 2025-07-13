package ru.ancndz.bandagez;

import java.util.function.Function;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import ru.ancndz.bandagez.config.ClientModConfiguration;
import ru.ancndz.bandagez.config.ConfigEntry;
import ru.ancndz.bandagez.config.ModConfiguration;
import ru.ancndz.bandagez.config.ServerModConfiguration;

public class NeoForgeConfigurationHandler {

    public static void init(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, buildClientNeoModConfigSpec());
        container.registerConfig(ModConfig.Type.SERVER, buildServerNeoModConfigSpec());
    }

    private static ModConfigSpec buildClientNeoModConfigSpec() {
        final Pair<ClientModConfiguration<ModConfigSpec.ConfigValue<?>>, ModConfigSpec> specPair =
                new ModConfigSpec.Builder()
                        .configure(builder -> new ClientModConfiguration<>() {
                            final NeoForgeConfigConverter converter = new NeoForgeConfigConverter(builder);

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

    private static ModConfigSpec buildServerNeoModConfigSpec() {
        final Pair<ServerModConfiguration<ModConfigSpec.ConfigValue<?>>, ModConfigSpec> specPair =
                new ModConfigSpec.Builder()
                        .configure(builder -> new ServerModConfiguration<>() {
                            final NeoForgeConfigConverter converter = new NeoForgeConfigConverter(builder);

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

    private static <V extends Comparable<? super V>> Function<ConfigEntry<V>, ModConfigSpec.ConfigValue<V>> getForgeValueConverter(ModConfigSpec.Builder builder) {
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
