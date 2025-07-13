package ru.ancndz.bandagez;

import java.util.function.Function;
import net.minecraftforge.common.ForgeConfigSpec;
import ru.ancndz.bandagez.config.ConfigEntry;

public class ForgeConfigConverter {

    final ForgeConfigSpec.Builder builder;

    public ForgeConfigConverter(ForgeConfigSpec.Builder builder) {
        this.builder = builder;
    }

    public <V extends Comparable<? super V>> Function<? extends ForgeConfigSpec.ConfigValue<V>, V> toValue() {
        return ForgeConfigSpec.ConfigValue::get;
    }

    public <V extends Comparable<? super V>> Function<ConfigEntry<V>, ? extends ForgeConfigSpec.ConfigValue<V>> toConfigImplValue() {
        return configEntry -> {
            var configValue = builder.comment(configEntry.getComment())
                    .translation(configEntry.getTranslation());
            if (configEntry.getRange() != null) {
                Object value = configEntry.getValue();
                if (value instanceof Integer) {
                    return (ForgeConfigSpec.ConfigValue<V>) configValue.defineInRange(configEntry.getPath(), (Integer) value, (Integer) configEntry.getRange().getMinimum(), (Integer) configEntry.getRange().getMaximum());
                } else if (value instanceof Double) {
                    return (ForgeConfigSpec.ConfigValue<V>) configValue.defineInRange(configEntry.getPath(), (Double) value, (Double) configEntry.getRange().getMinimum(), (Double) configEntry.getRange().getMaximum());
                }
            }
            return configValue.define(configEntry.getPath(), configEntry.getValue());
        };
    }
}