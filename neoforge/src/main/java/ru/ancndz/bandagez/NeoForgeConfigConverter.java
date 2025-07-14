package ru.ancndz.bandagez;

import java.util.function.Function;
import net.neoforged.neoforge.common.ModConfigSpec;
import ru.ancndz.bandagez.config.ConfigEntry;

public class NeoForgeConfigConverter {

    public static <V extends Comparable<? super V>> Function<? extends ModConfigSpec.ConfigValue<V>, V> toValue() {
        return ModConfigSpec.ConfigValue::get;
    }

    public static <V extends Comparable<? super V>> Function<ConfigEntry<V>, ? extends ModConfigSpec.ConfigValue<V>> toConfigImplValue(ModConfigSpec.Builder builder) {
        return configEntry -> {
            var configValue = builder.comment(configEntry.getComment())
                    .translation(configEntry.getTranslation());
            if (configEntry.getRange() != null) {
                Object value = configEntry.getValue();
                if (value instanceof Integer) {
                    return (ModConfigSpec.ConfigValue<V>) configValue.defineInRange(configEntry.getPath(), (Integer) value, (Integer) configEntry.getRange().getMinimum(), (Integer) configEntry.getRange().getMaximum());
                } else if (value instanceof Double) {
                    return (ModConfigSpec.ConfigValue<V>) configValue.defineInRange(configEntry.getPath(), (Double) value, (Double) configEntry.getRange().getMinimum(), (Double) configEntry.getRange().getMaximum());
                }
            }
            return configValue.define(configEntry.getPath(), configEntry.getValue());
        };
    }
}