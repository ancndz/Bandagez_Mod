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
                    public <V extends Comparable<? super V>> Function<ForgeConfigSpec.ConfigValue<V>, V> getConverter() {
                        return ForgeConfigSpec.ConfigValue::get;
                    }

                    @Override
                    protected <V extends Comparable<? super V>> Function<ConfigEntry<V>, ForgeConfigSpec.ConfigValue<V>> getValueConverter() {
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
                    public <V extends Comparable<? super V>> Function<ForgeConfigSpec.ConfigValue<V>, V> getConverter() {
                        return ForgeConfigSpec.ConfigValue::get;
                    }

                    @Override
                    protected <V extends Comparable<? super V>> Function<ConfigEntry<V>, ForgeConfigSpec.ConfigValue<V>> getValueConverter() {
                        return getForgeValueConverter(builder);
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

