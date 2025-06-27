package ru.ancndz.bandagez.mod;

import static ru.ancndz.bandagez.item.GrassDropModifier.GRASS_DROP_MODIFIER;

import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.ancndz.bandagez.effect.ModMobEffects;
import ru.ancndz.bandagez.item.GrassDropModifier;
import ru.ancndz.bandagez.item.ModItems;
import ru.ancndz.bandagez.sound.ModSoundEvents;

@Mod(BandagezMod.MODID)
public class BandagezMod {

    public static final String MODID = "bandagez";

    public BandagezMod(FMLJavaModLoadingContext context) {
        final BusGroup modEventBus = context.getModBusGroup();
        BandagezModConfig.init(context);
        GrassDropModifier.LOOT_MODIFIERS.register(GRASS_DROP_MODIFIER, GrassDropModifier.CODEC_SUPPLIER);
        ModSoundEvents.SOUNDS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModItems.CREATIVE_MODE_TABS.register(modEventBus);
        ModMobEffects.EFFECTS.register(modEventBus);
        GrassDropModifier.LOOT_MODIFIERS.register(modEventBus);
    }

}