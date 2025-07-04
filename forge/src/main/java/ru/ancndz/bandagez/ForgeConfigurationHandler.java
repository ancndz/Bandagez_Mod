package ru.ancndz.bandagez;

import java.util.function.Function;
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
            public Function<ForgeConfigSpec.ConfigValue<?>, Object> getConverter() {
                return ForgeConfigSpec.ConfigValue::get;
            }

            @Override
            protected Function<ConfigEntry<?>, ForgeConfigSpec.ConfigValue<?>> getValueConverter() {
                return getForgeValueConverter(builder);
            }
        };

        ModConfiguration.setClientConfig(config);
        return builder.build();
    }

    private static ForgeConfigSpec buildServerForgeConfigSpec() {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        final var config = new ServerModConfiguration<ForgeConfigSpec.ConfigValue<?>>() {

            @Override
            public Function<ForgeConfigSpec.ConfigValue<?>, Object> getConverter() {
                return ForgeConfigSpec.ConfigValue::get;
            }

            @Override
            protected Function<ConfigEntry<?>, ForgeConfigSpec.ConfigValue<?>> getValueConverter() {
                return getForgeValueConverter(builder);
            }
        };

        ModConfiguration.setServerConfig(config);
        return builder.build();
    }


    private static Function<ConfigEntry<?>, ForgeConfigSpec.ConfigValue<?>> getForgeValueConverter(ForgeConfigSpec.Builder builder) {
        return configEntry -> {
            var configValue = builder.comment(configEntry.getComment())
                    .translation(configEntry.getTranslation());
            if (configEntry.getRange() != null) {
                Object value = configEntry.getValue();
                if (value instanceof Integer) {
                    return (ForgeConfigSpec.ConfigValue<Integer>) configValue.defineInRange(configEntry.getPath(), (Integer) value, (Integer) configEntry.getRange().getMinimum(), (Integer) configEntry.getRange().getMaximum());
                } else if (value instanceof Double) {
                    return (ForgeConfigSpec.ConfigValue<Double>) configValue.defineInRange(configEntry.getPath(), (Double) value, (Double) configEntry.getRange().getMinimum(), (Double) configEntry.getRange().getMaximum());
                }
            }
            return configValue.define(configEntry.getPath(), configEntry.getValue());
        };
    }
}

