package ru.ancndz.bandagez.item;

import static ru.ancndz.bandagez.mod.BandagezMod.ITEMS;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import ru.ancndz.bandagez.item.bandage.BandageItem;
import ru.ancndz.bandagez.item.bandage.BandageTypes;
import ru.ancndz.bandagez.item.bandage.HealingBandageTypes;
import ru.ancndz.bandagez.mod.BandagezMod;

public class Items {

    public static final String FLORAL_STRING_NAME = "floral_string";

    public static final RegistryObject<Item> FLORAL_STRING_ITEM = ITEMS.register(FLORAL_STRING_NAME,
            () -> new Item(new Item.Properties().setId(ITEMS.key(FLORAL_STRING_NAME))));

    public static final RegistryObject<Item> EMPTY_BAND_ITEM = ITEMS.register(BandageTypes.EMPTY.getName(),
            () -> new BandageItem(BandageTypes.EMPTY,
                    new Item.Properties().stacksTo(8).setId(ITEMS.key(BandageTypes.EMPTY.getName()))));

    public static final RegistryObject<Item> HEMOSTATIC_BAND_ITEM = ITEMS.register(BandageTypes.HEMOSTATIC.getName(),
            () -> new BandageItem(BandageTypes.HEMOSTATIC,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(BandageTypes.HEMOSTATIC.getName()))));

    public static final RegistryObject<Item> SMALL_BAND_ITEM = ITEMS.register(HealingBandageTypes.SMALL.getName(),
            () -> new BandageItem(HealingBandageTypes.SMALL,
                    new Item.Properties().stacksTo(16).setId(ITEMS.key(HealingBandageTypes.SMALL.getName()))));

    public static final RegistryObject<Item> MEDIUM_BAND_ITEM = ITEMS.register(HealingBandageTypes.MEDIUM.getName(),
            () -> new BandageItem(HealingBandageTypes.MEDIUM,
                    new Item.Properties().stacksTo(8).setId(ITEMS.key(HealingBandageTypes.MEDIUM.getName()))));

    public static final RegistryObject<Item> LARGE_BAND_ITEM = ITEMS.register(HealingBandageTypes.LARGE.getName(),
            () -> new BandageItem(HealingBandageTypes.LARGE,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(HealingBandageTypes.LARGE.getName()))));

    public static final RegistryObject<Item> ANTI_BIOTIC_BAND_ITEM = ITEMS.register(BandageTypes.ANTI_BIOTIC.getName(),
            () -> new BandageItem(BandageTypes.ANTI_BIOTIC,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(BandageTypes.ANTI_BIOTIC.getName()))));

    public static final RegistryObject<Item> MAGIC_BAND_ITEM = ITEMS.register(BandageTypes.MAGIC.getName(),
            () -> new BandageItem(BandageTypes.MAGIC,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(BandageTypes.MAGIC.getName()))));

    public static final RegistryObject<Item> STIMULANT_BAND_ITEM = ITEMS.register(BandageTypes.STIMULANT.getName(),
            () -> new BandageItem(BandageTypes.STIMULANT,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(BandageTypes.STIMULANT.getName()))));

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB =
            BandagezMod.CREATIVE_MODE_TABS.register("bandages_tab",
                    () -> CreativeModeTab.builder()
                            .title(Component.translatable("bandagez.bandages_tab"))
                            .withTabsBefore(CreativeModeTabs.FOOD_AND_DRINKS)
                            .icon(() -> SMALL_BAND_ITEM.get().getDefaultInstance())
                            .displayItems((parameters, output) -> {
                                output.accept(FLORAL_STRING_ITEM.get());
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

    public static void init() {
        FLORAL_STRING_ITEM.hashCode();
        EMPTY_BAND_ITEM.hashCode();
        HEMOSTATIC_BAND_ITEM.hashCode();
        SMALL_BAND_ITEM.hashCode();
        MEDIUM_BAND_ITEM.hashCode();
        LARGE_BAND_ITEM.hashCode();
        ANTI_BIOTIC_BAND_ITEM.hashCode();
        MAGIC_BAND_ITEM.hashCode();
        STIMULANT_BAND_ITEM.hashCode();
        EXAMPLE_TAB.hashCode();
    }

    private Items() {
    }
}
