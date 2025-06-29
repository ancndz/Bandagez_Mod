package ru.ancndz.bandagez.registration;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModList;
import net.neoforged.fml.javafmlmod.FMLModContainer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public class NeoForgeRegistrationFactory implements RegistrationProvider.Factory {

    @Override
    public <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        final var containerOpt = ModList.get().getModContainerById(modId);
        if (containerOpt.isEmpty())
            throw new NullPointerException("Cannot find mod container for id " + modId);
        final var cont = containerOpt.get();
        if (cont instanceof FMLModContainer fmlModContainer) {
            final var register = DeferredRegister.create(resourceKey, modId);
            register.register(fmlModContainer.getEventBus());
            return new Provider<>(modId, register);
        } else {
            throw new ClassCastException("The container of the mod " + modId + " is not a FML one!");
        }
    }

    private static class Provider<T> implements RegistrationProvider<T> {

        private final String modId;

        private final DeferredRegister<T> registry;

        private final Set<RegistryObject<T>> entries = new HashSet<>();

        private Provider(String modId, DeferredRegister<T> registry) {
            this.modId = modId;
            this.registry = registry;
        }

        @Override
        public String getModId() {
            return modId;
        }

        @Override
        public ResourceKey<? extends Registry<T>> getRegistryKey() {
            return registry.getRegistryKey();
        }

        @Override
        @SuppressWarnings({ "unchecked" })
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            final DeferredHolder<T, I> obj = registry.register(name, supplier);
            final RegistryObject<I> ro = new RegistryObject<>() {

                @Override
                public ResourceKey<I> getResourceKey() {
                    // noinspection unchecked
                    return (ResourceKey<I>) ResourceKey.create(registry.getRegistryKey(), obj.getId());
                }

                @Override
                public ResourceLocation getId() {
                    return obj.getId();
                }

                @Override
                public I get() {
                    return obj.get();
                }

                @Override
                public Holder<I> getHolder() {
                    // noinspection unchecked
                    return (Holder<I>) obj.getDelegate();
                }
            };
            entries.add((RegistryObject<T>) ro);
            return ro;
        }

        @Override
        public Set<RegistryObject<T>> getEntries() {
            return Collections.unmodifiableSet(entries);
        }
    }
}