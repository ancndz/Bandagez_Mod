package ru.ancndz.bandagez.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import ru.ancndz.bandagez.BandagezMod;
import ru.ancndz.bandagez.effect.particle.BleedingParticle;
import ru.ancndz.bandagez.effect.particle.ModParticles;

@EventBusSubscriber(modid = BandagezMod.MODID, value = Dist.CLIENT)
public class NeoForgeClientEventHandler {

    @SubscribeEvent
    public static void onRegisterParticleProvidersEvent(RegisterParticleProvidersEvent event) {
        event.registerSprite(ModParticles.BLEEDING_PARTICLE.get(), BleedingParticle::createParticle);
    }

}
