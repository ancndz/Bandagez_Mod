package ru.ancndz.bandagez.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.ancndz.bandagez.BandagezMod;
import ru.ancndz.bandagez.effect.particle.BleedingParticle;
import ru.ancndz.bandagez.effect.particle.ModParticles;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BandagezMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientEventHandler {

    @SubscribeEvent
    public static void onRegisterParticleProvidersEvent(RegisterParticleProvidersEvent event) {
        event.registerSprite(ModParticles.BLEEDING_PARTICLE.get(), BleedingParticle::createParticle);
    }

}
