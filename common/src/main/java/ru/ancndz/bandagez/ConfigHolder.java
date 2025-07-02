package ru.ancndz.bandagez;

public final class ConfigHolder {

    private static IModConfig MOD_CONFIG;

    private ConfigHolder() {
    }

    public static void setConfig(IModConfig config) {
        ConfigHolder.MOD_CONFIG = config;
    }

    public static IModConfig getConfig() {
        if (MOD_CONFIG == null) {
            throw new IllegalStateException("Config not initialized!");
        }
        return MOD_CONFIG;
    }
}