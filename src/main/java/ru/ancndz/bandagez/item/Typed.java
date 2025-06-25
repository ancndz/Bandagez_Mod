package ru.ancndz.bandagez.item;

public interface Typed<T> {

    T getType();

    default boolean isType(T type) {
        return getType() == type;
    }
}
