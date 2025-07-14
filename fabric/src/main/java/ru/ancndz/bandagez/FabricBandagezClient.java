package ru.ancndz.bandagez;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import ru.ancndz.bandagez.effect.particle.BleedingParticle;
import ru.ancndz.bandagez.effect.particle.ModParticles;

public class FabricBandagezClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.BLEEDING_PARTICLE.get(), BleedingParticle::createParticle);
    }

}
