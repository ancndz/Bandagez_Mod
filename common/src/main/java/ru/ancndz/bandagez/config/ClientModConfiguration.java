package ru.ancndz.bandagez.config;

public abstract class ClientModConfiguration<T> extends ModConfiguration<T> {

    private static final class Options {
        private static final String SHOW_PARTICLES_OPTION = "showParticles";
    }

    public ClientModConfiguration() {
        super();

        final ConfigEntry<Boolean> showParticles = ConfigEntry.<Boolean>builder()
            .value(Boolean.TRUE)
            .comment("Show particles on bleeding effects")
            .translation("bandagez.config.show_bleeding_particles")
            .path(Options.SHOW_PARTICLES_OPTION)
            .build();

        putValue(showParticles);
    }

    public Boolean getShowParticles() {
        return (Boolean) getConverter().apply(getValue(Options.SHOW_PARTICLES_OPTION));
    }

}