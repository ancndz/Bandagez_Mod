package ru.ancndz.bandagez.config;

public abstract class ClientModConfiguration<T> extends ModConfiguration<T> {

    public ClientModConfiguration() {
        super();

        final ConfigEntry<Boolean> showParticles = ConfigEntry.<Boolean>builder()
            .value(Boolean.TRUE)
            .comment("Show particles on bleeding effects")
            .translation("bandagez.config.show_bleeding_particles")
            .path("showParticles")
            .build();

        putValue(showParticles);
    }

    public Boolean getShowParticles() {
        return (Boolean) getConverter().apply(getValue("showParticles"));
    }

}