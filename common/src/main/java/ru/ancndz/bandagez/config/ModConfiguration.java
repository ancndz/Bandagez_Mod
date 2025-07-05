package ru.ancndz.bandagez.config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract sealed class ModConfiguration<T> permits ServerModConfiguration, ClientModConfiguration {
    private static ClientModConfiguration<?> client = null;
    private static ServerModConfiguration<?> server = null;

    private final Map<String, T> values = new HashMap<>();

    private <TT extends T> TT getValueRaw(String path) {
        return (TT) this.values.get(path);
    }

    protected <V extends Comparable<? super V>> V getValue(String path) {
        return this.<V>getConverter().apply(getValueRaw(path));
    }

    protected <V extends Comparable<? super V>> void putValue(ConfigEntry<V> configEntry) {
        values.put(configEntry.getPath(), this.<V>getValueConverter().apply(configEntry));
    }

    protected abstract <V extends Comparable<? super V>> Function<? extends T, V> getConverter();

    protected abstract <V extends Comparable<? super V>> Function<ConfigEntry<V>, ? extends T> getValueConverter();

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
