package ru.ancndz.bandagez.config;

import java.util.HashMap;
import java.util.Map;

public abstract sealed class ModConfiguration<T> permits ServerModConfiguration, ClientModConfiguration {
    private static ClientModConfiguration<?> client = null;
    private static ServerModConfiguration<?> server = null;

    private final Map<String, T> values = new HashMap<>();

    protected <TT extends T> TT getValueRaw(String path) {
        return (TT) this.values.get(path);
    }

    protected Map<String, T> getValues() {
        return this.values;
    }

    protected abstract <V extends Comparable<? super V>> V getValue(String path);

    protected abstract <V extends Comparable<? super V>> void putValue(ConfigEntry<V> configEntry);

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
            throw new IllegalStateException("Server configuration is not initialized");
        }
        return server;
    }

}
