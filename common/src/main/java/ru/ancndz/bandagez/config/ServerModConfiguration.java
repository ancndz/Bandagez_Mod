package ru.ancndz.bandagez.config;

public abstract class ServerModConfiguration<T> extends ModConfiguration<T> {

    private static final class Options {
    }

    public ServerModConfiguration() {
        super();
        // Server configuration can be initialized here if needed
    }
}