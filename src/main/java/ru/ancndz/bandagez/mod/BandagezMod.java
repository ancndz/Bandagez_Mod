package ru.ancndz.bandagez.mod;

import static ru.ancndz.bandagez.item.GrassDropModifier.GRASS_DROP_MODIFIER;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.ancndz.bandagez.effect.ModEffects;
import ru.ancndz.bandagez.item.GrassDropModifier;
import ru.ancndz.bandagez.item.ModItems;
import ru.ancndz.bandagez.sound.ModSoundEvent;

@Mod(BandagezMod.MODID)
public class BandagezMod {

    public static final String MODID = "bandagez";

    public BandagezMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BandagezModConfig.init();

        GrassDropModifier.LOOT_MODIFIERS.register(GRASS_DROP_MODIFIER, GrassDropModifier.CODEC);

        ModSoundEvent.SOUNDS.register(modEventBus);
        ModEffects.EFFECTS.register(modEventBus);
        GrassDropModifier.LOOT_MODIFIERS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

}