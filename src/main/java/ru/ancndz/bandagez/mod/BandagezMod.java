package ru.ancndz.bandagez.mod;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import ru.ancndz.bandagez.effect.Bleeding;
import ru.ancndz.bandagez.effect.FreshBandage;
import ru.ancndz.bandagez.item.Bandage;
import ru.ancndz.bandagez.item.BandageTypes;

@Mod(BandagezMod.MODID)
public class BandagezMod {

    public static final String MODID = "bandagez";

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> SMALL_BANDAGE_USE =
            BandagezMod.SOUNDS.register("item.band.small_bandage_inuse",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.small_bandage_inuse")));

    public static final RegistryObject<SoundEvent> MEDIUM_BANDAGE_USE = BandagezMod.SOUNDS.register(
            "item.band.medium_bandage_inuse",
            () -> SoundEvent.createVariableRangeEvent(
                    ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.medium_bandage_inuse")));

    public static final RegistryObject<SoundEvent> BANDAGE_USE_START =
            BandagezMod.SOUNDS.register("item.band.bandage_use_start",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.bandage_use_start")));

    public static final RegistryObject<SoundEvent> BANDAGE_USE_MID =
            BandagezMod.SOUNDS.register("item.band.bandage_use_mid",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.bandage_use_mid")));

    public static final RegistryObject<SoundEvent> BANDAGE_USE_END =
            BandagezMod.SOUNDS.register("item.band.bandage_use_end",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "item.band.bandage_use_end")));

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> EMPTY_BAND_ITEM = BandagezMod.ITEMS.register("empty_band",
            () -> new Bandage(BandageTypes.EMPTY, new Item.Properties().stacksTo(8)));

    public static final RegistryObject<Item> HEMOSTATIC_BAND_ITEM = BandagezMod.ITEMS.register("hemostatic_band",
            () -> new Bandage(BandageTypes.HEMOSTATIC, new Item.Properties().stacksTo(4)));

    public static final RegistryObject<Item> SMALL_BAND_ITEM = BandagezMod.ITEMS.register("small_band",
            () -> new Bandage(BandageTypes.SMALL, new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> MEDIUM_BAND_ITEM = BandagezMod.ITEMS.register("medium_band",
            () -> new Bandage(BandageTypes.MEDIUM, new Item.Properties().stacksTo(8)));

    public static final RegistryObject<Item> LARGE_BAND_ITEM = BandagezMod.ITEMS.register("large_band",
            () -> new Bandage(BandageTypes.LARGE, new Item.Properties().stacksTo(4)));

    public static final RegistryObject<Item> ANTI_BIOTIC_BAND_ITEM = BandagezMod.ITEMS.register("antibiotic_band",
            () -> new Bandage(BandageTypes.ANTI_BIOTIC, new Item.Properties().stacksTo(4)));

    public static final RegistryObject<Item> MAGIC_BAND_ITEM = BandagezMod.ITEMS.register("magic_band",
            () -> new Bandage(BandageTypes.MAGIC, new Item.Properties().stacksTo(4)));

    public static final RegistryObject<Item> STIMULANT_BAND_ITEM = BandagezMod.ITEMS.register("stimulant_band",
            () -> new Bandage(BandageTypes.STIMULANT, new Item.Properties().stacksTo(4)));

    public static final RegistryObject<Item> MOD_ITEM =
            BandagezMod.ITEMS.register("mod_icon_item", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("bandages_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable(MODID + ".bandages_tab"))
                    .withTabsBefore(CreativeModeTabs.FOOD_AND_DRINKS)
                    .icon(() -> MOD_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(EMPTY_BAND_ITEM.get());
                        output.accept(HEMOSTATIC_BAND_ITEM.get());
                        output.accept(SMALL_BAND_ITEM.get());
                        output.accept(MEDIUM_BAND_ITEM.get());
                        output.accept(LARGE_BAND_ITEM.get());
                        output.accept(ANTI_BIOTIC_BAND_ITEM.get());
                        output.accept(MAGIC_BAND_ITEM.get());
                        output.accept(STIMULANT_BAND_ITEM.get());
                    })
                    .build());

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MODID);

    public static final RegistryObject<MobEffect> BLEEDING =
            EFFECTS.register("bleeding", () -> new Bleeding(false, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> HARD_BLEEDING =
            EFFECTS.register("hard_bleeding", () -> new Bleeding(true, MobEffectCategory.HARMFUL, 13458603));

    public static final RegistryObject<MobEffect> FRESH_BANDAGE =
            EFFECTS.register("fresh_bandage", () -> new FreshBandage(MobEffectCategory.NEUTRAL, 13458603));

    public BandagezMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        SOUNDS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        EFFECTS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info("{}{}", Config.magicNumberIntroduction, Config.magicNumber);
    }
}