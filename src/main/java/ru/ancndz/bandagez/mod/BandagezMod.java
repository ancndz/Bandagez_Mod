package ru.ancndz.bandagez.mod;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import ru.ancndz.bandagez.effect.Effects;
import ru.ancndz.bandagez.item.GrassDropModifier;
import ru.ancndz.bandagez.item.Items;
import ru.ancndz.bandagez.sound.Sounds;

@Mod(BandagezMod.MODID)
public class BandagezMod {

    public static final String MODID = "bandagez";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);

	public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister
			.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MODID);

	public BandagezMod(final FMLJavaModLoadingContext context) {
		final BusGroup modEventBus = context.getModBusGroup();

		Effects.init();
		Sounds.init();
		Items.init();

		LOOT_MODIFIERS.register("grass_drop_modifier", GrassDropModifier.CODEC_SUPPLIER);

        SOUNDS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        EFFECTS.register(modEventBus);
		LOOT_MODIFIERS.register(modEventBus);
    }

}