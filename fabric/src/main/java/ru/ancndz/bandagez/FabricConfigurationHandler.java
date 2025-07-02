package ru.ancndz.bandagez;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.fabricmc.loader.api.FabricLoader;

public class FabricConfigurationHandler {

    private static CommentedFileConfig config;

    public static void init(FabricLoader instance) {
        config = CommentedFileConfig.builder(instance.getConfigDir().resolve("bandagez-client.toml"))
            .autosave()
            .autoreload()
            .build();

        config.load();
        if (!config.isEmpty()) {
            if (!config.contains("showParticles")) {
                config.set("showParticles", true);
                config.setComment("showParticles", "Show particles on bleeding effects");
            }
        } else {
            config.set("showParticles", true);
            config.setComment("showParticles", "Show particles on bleeding effects");
        }
        ConfigHolder.setConfig(new IModConfig() {

            @Override
            public Boolean showParticles() {
                return config.get("showParticles");
            }
        });
    }
}
