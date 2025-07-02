package ru.ancndz.bandagez.config;

import org.apache.commons.lang3.Range;

public class ConfigEntry<T> {

    private final T value;

    private final Range<T> range;

    private final String comment;

    private final String translation;

    private final String path;

    private ConfigEntry(T value, String comment, String translation, String path, Range<T> range) {
        this.value = value;
        this.comment = comment;
        this.translation = translation;
        this.path = path;
        this.range = range;
    }

    public T getValue() {
        return value;
    }

    public Range<T> getRange() {
        return range;
    }

    public String getComment() {
        return comment;
    }

    public String getTranslation() {
        return translation;
    }

    public String getPath() {
        return path;
    }

    public static <R> Builder<R> builder() {
        return new Builder<>();
    }

    public static class Builder<T> {

        private T value;

        private Range<T> range;

        private String comment;

        private String translation;

        private String path;

        public Builder<T> value(T value) {
            this.value = value;
            return this;
        }

        public Builder<T> range(Range<T> range) {
            this.range = range;
            return this;
        }

        public Builder<T> comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder<T> translation(String translation) {
            this.translation = translation;
            return this;
        }

        public Builder<T> path(String path) {
            this.path = path;
            return this;
        }

        public ConfigEntry<T> build() {
            return new ConfigEntry<>(value, comment, translation, path, range);
        }
    }

}