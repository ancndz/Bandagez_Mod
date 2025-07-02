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
                    public Function<ModConfigSpec.ConfigValue<?>, Object> getConverter() {
                        return ModConfigSpec.ConfigValue::get;
                    }

                    @Override
                    protected Function<ConfigEntry<?>, ModConfigSpec.ConfigValue<?>> getValueConverter() {
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
                    public Function<ModConfigSpec.ConfigValue<?>, Object> getConverter() {
                        return ModConfigSpec.ConfigValue::get;
                    }

                    @Override
                    protected Function<ConfigEntry<?>, ModConfigSpec.ConfigValue<?>> getValueConverter() {
                        return getForgeValueConverter(builder);
                    }
                });
        ModConfiguration.setServerConfig(specPair.getLeft());
        return specPair.getRight();
    }


    private static Function<ConfigEntry<?>, ModConfigSpec.ConfigValue<?>> getForgeValueConverter(ModConfigSpec.Builder builder) {
        return configEntry -> {
            ModConfigSpec.Builder configValue = builder.comment(configEntry.getComment())
                .translation(configEntry.getTranslation());
            if (configEntry.getRange() != null) {
                Object value = configEntry.getValue();
                if (value instanceof Integer) {
                    return (ModConfigSpec.ConfigValue<Integer>) configValue.defineInRange(configEntry.getPath(), (Integer) value, (Integer) configEntry.getRange().getMinimum(), (Integer) configEntry.getRange().getMaximum());
                } else if (value instanceof Double) {
                    return (ModConfigSpec.ConfigValue<Double>) configValue.defineInRange(configEntry.getPath(), (Double) value, (Double) configEntry.getRange().getMinimum(), (Double) configEntry.getRange().getMaximum());
                }
            }
            return configValue.define(configEntry.getPath(), configEntry.getValue());
        };
    }
}
