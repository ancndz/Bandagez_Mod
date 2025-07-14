package ru.ancndz.bandagez.config;

public abstract non-sealed class ClientModConfiguration<T> extends ModConfiguration<T> {

    private static final class Options {
        private static final String SHOW_PARTICLES_OPTION = "showParticles";
    }

    public ClientModConfiguration() {
        putValue(ConfigEntry.<Boolean>builder()
                .value(Boolean.TRUE)
                .comment("Show particles on bleeding effects")
                .translation("bandagez.config.show_bleeding_particles")
                .path(Options.SHOW_PARTICLES_OPTION)
                .build());
    }

    public Boolean getShowParticles() {
        return getValue(Options.SHOW_PARTICLES_OPTION);
    }

}