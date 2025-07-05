package ru.ancndz.bandagez;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import ru.ancndz.bandagez.config.ClientModConfiguration;
import ru.ancndz.bandagez.config.ConfigEntry;
import ru.ancndz.bandagez.config.ModConfiguration;
import ru.ancndz.bandagez.config.ServerModConfiguration;

import java.util.function.Function;

public class NeoForgeConfigurationHandler {

    public static void init(ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, buildClientNeoModConfigSpec());
        container.registerConfig(ModConfig.Type.SERVER, buildServerNeoModConfigSpec());
    }

    private static ModConfigSpec buildClientNeoModConfigSpec() {
        final Pair<ClientModConfiguration<ModConfigSpec.ConfigValue<?>>, ModConfigSpec> specPair =
            new ModConfigSpec.Builder()
                .configure(builder -> new ClientModConfiguration<>() {
                    @Override
                    public <V extends Comparable<? super V>> Function<ModConfigSpec.ConfigValue<V>, V> getConverter() {
                        return ModConfigSpec.ConfigValue::get;
                    }

                    @Override
                    public <V extends Comparable<? super V>> Function<ConfigEntry<V>, ModConfigSpec.ConfigValue<V>> getValueConverter() {
                        return getForgeValueConverter(builder);
                    }
                });
        ModConfiguration.setClientConfig(specPair.getLeft());
        return specPair.getRight();
    }

    private static ModConfigSpec buildServerNeoModConfigSpec() {
        final Pair<ServerModConfiguration<ModConfigSpec.ConfigValue<?>>, ModConfigSpec> specPair =
            new ModConfigSpec.Builder()
                .configure(builder -> new ServerModConfiguration<>() {
                    @Override
                    public <V extends Comparable<? super V>> Function<ModConfigSpec.ConfigValue<V>, V> getConverter() {
                        return ModConfigSpec.ConfigValue::get;
                    }

                    @Override
                    public <V extends Comparable<? super V>> Function<ConfigEntry<V>, ModConfigSpec.ConfigValue<V>> getValueConverter() {
                        return getForgeValueConverter(builder);
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
