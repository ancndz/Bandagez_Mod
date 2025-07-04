package ru.ancndz.bandagez;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import ru.ancndz.bandagez.config.ClientModConfiguration;
import ru.ancndz.bandagez.config.ConfigEntry;
import ru.ancndz.bandagez.config.ModConfiguration;
import ru.ancndz.bandagez.config.ServerModConfiguration;

import java.util.function.Function;

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
                    public Function<ForgeConfigSpec.ConfigValue<?>, Object> getConverter() {
                        return ForgeConfigSpec.ConfigValue::get;
                    }

                    @Override
                    protected Function<ConfigEntry<?>, ForgeConfigSpec.ConfigValue<?>> getValueConverter() {
                        return getForgeValueConverter(builder);
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
                    public Function<ForgeConfigSpec.ConfigValue<?>, Object> getConverter() {
                        return ForgeConfigSpec.ConfigValue::get;
                    }

                    @Override
                    protected Function<ConfigEntry<?>, ForgeConfigSpec.ConfigValue<?>> getValueConverter() {
                        return getForgeValueConverter(builder);
                    }
                });
        ModConfiguration.setServerConfig(specPair.getLeft());
        return specPair.getRight();
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

