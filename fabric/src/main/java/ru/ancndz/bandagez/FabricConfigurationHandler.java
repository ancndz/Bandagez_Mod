package ru.ancndz.bandagez;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import ru.ancndz.bandagez.config.ClientModConfiguration;
import ru.ancndz.bandagez.config.ConfigEntry;
import ru.ancndz.bandagez.config.ModConfiguration;
import ru.ancndz.bandagez.config.ServerModConfiguration;

import java.util.function.Function;

public class FabricConfigurationHandler {

    private static CommentedFileConfig clientConfig;

    private static CommentedFileConfig serverConfig;

    public static void init(FabricLoader instance) {
        initClientConfig(instance);
        initServerConfig(instance);
    }

    private static void initClientConfig(FabricLoader instance) {
        clientConfig = CommentedFileConfig.builder(instance.getConfigDir().resolve("bandagez-client.toml"))
            .autosave()
            .autoreload()
            .build();

        clientConfig.load();
        ModConfiguration.setClientConfig(new ClientModConfiguration<>() {
            @Override
            protected Object getValue(String path) {
                return clientConfig.get(path);
            }

            @Override
            protected void putValue(ConfigEntry<?> configEntry) {
                if (!clientConfig.contains(configEntry.getPath())) {
                    clientConfig.set(configEntry.getPath(), configEntry.getValue());
                    if (configEntry.getTranslation() != null) {
                        clientConfig.setComment(configEntry.getPath(), Component.translatable(configEntry.getTranslation()).getVisualOrderText().toString());
                    }
                    clientConfig.setComment(configEntry.getPath(), configEntry.getComment());
                } else {
                    if (configEntry.getTranslation() != null) {
                        clientConfig.setComment(configEntry.getPath(), Component.translatable(configEntry.getTranslation()).getVisualOrderText().toString());
                    } else {
                        clientConfig.setComment(configEntry.getPath(), configEntry.getComment());
                    }
                }
            }

            @Override
            protected Function<Object, Object> getConverter() {
                return Function.identity();
            }

            @Override
            protected Function<ConfigEntry<?>, Object> getValueConverter() {
                throw new UnsupportedOperationException();
            }
        });
    }

    private static void initServerConfig(FabricLoader instance) {
        serverConfig = CommentedFileConfig.builder(instance.getConfigDir().resolve("bandagez-server.toml"))
            .autosave()
            .autoreload()
            .build();

        serverConfig.load();
        ModConfiguration.setServerConfig(new ServerModConfiguration<>() {
            @Override
            protected Object getValue(String path) {
                return serverConfig.get(path);
            }

            @Override
            protected void putValue(ConfigEntry<?> configEntry) {
                if (!serverConfig.contains(configEntry.getPath())) {
                    serverConfig.set(configEntry.getPath(), configEntry.getValue());
                    if (configEntry.getTranslation() != null) {
                        serverConfig.setComment(configEntry.getPath(), Component.translatable(configEntry.getTranslation()).getVisualOrderText().toString());
                    }
                } else {
                    if (configEntry.getTranslation() != null) {
                        serverConfig.setComment(configEntry.getPath(), Component.translatable(configEntry.getTranslation()).getVisualOrderText().toString());
                    } else {
                        serverConfig.setComment(configEntry.getPath(), configEntry.getComment());
                    }
                }
            }

            @Override
            protected Function<Object, Object> getConverter() {
                return Function.identity();
            }

            @Override
            protected Function<ConfigEntry<?>, Object> getValueConverter() {
                throw new UnsupportedOperationException();
            }
        });
    }
}
