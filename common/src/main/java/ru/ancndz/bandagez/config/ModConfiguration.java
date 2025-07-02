package ru.ancndz.bandagez.config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class ModConfiguration<T> {

    private static ClientModConfiguration<?> client = null;
    private static ServerModConfiguration<?> server = null;

    private final Map<String, T> values;

    public ModConfiguration() {
        this.values = new HashMap<>();
    }

    protected T getValue(String path) {
        return values.get(path);
    }

    protected void putValue(ConfigEntry<?> configEntry) {
        values.put(configEntry.getPath(), getValueConverter().apply(configEntry));
    }

    protected abstract Function<T, Object> getConverter();

    protected abstract Function<ConfigEntry<?>, T> getValueConverter();

    public static void setClientConfig(ClientModConfiguration<?> clientModConfigurationConfig) {
        ModConfiguration.client = clientModConfigurationConfig;
    }

    public static ClientModConfiguration<?> getClientConfig() {
        if (client == null) {
            throw new IllegalStateException("Client configuration is not initialized");
        }
        return client;
    }

    public static void setServerConfig(ServerModConfiguration<?> serverModConfigurationConfig) {
        ModConfiguration.server = serverModConfigurationConfig;
    }

    public static ServerModConfiguration<?> getServerConfig() {
        if (server == null) {
            throw new IllegalStateException("Client configuration is not initialized");
        }
        return server;
    }

}
