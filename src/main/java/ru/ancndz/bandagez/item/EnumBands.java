package ru.ancndz.bandagez.item;

public enum EnumBands {

    STANDART, DETOXIC, ARMORED, MILKY;

    public static EnumBands getByMeta(int meta) {
        for (EnumBands type : values()) {
            if (type.ordinal() == meta)
                return type;
        }
        return null;
    }
}
