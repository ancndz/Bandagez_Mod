package ru.ancndz.bandagez.registry;

import java.util.function.Supplier;

public interface Registry {

    void register();

    <T> Supplier<T> register(String name, Supplier<T> item);
}
