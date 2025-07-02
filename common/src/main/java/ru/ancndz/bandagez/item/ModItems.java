package ru.ancndz.bandagez.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import ru.ancndz.bandagez.BandagezMod;
import ru.ancndz.bandagez.item.bandage.BandageItem;
import ru.ancndz.bandagez.item.bandage.BandageTypes;
import ru.ancndz.bandagez.item.bandage.HealingBandageTypes;
import ru.ancndz.bandagez.item.splint.SplintItem;
import ru.ancndz.bandagez.registration.RegistrationProvider;
import ru.ancndz.bandagez.registration.RegistryObject;

public class ModItems {

    public static void init() {
    }

    /**
     * The provider for items
     */
    public static final RegistrationProvider<Item> ITEMS =
            RegistrationProvider.get(BuiltInRegistries.ITEM, BandagezMod.MODID);

    /**
     * The provider for tabs
     */
    public static final RegistrationProvider<CreativeModeTab> TAB =
            RegistrationProvider.get(BuiltInRegistries.CREATIVE_MODE_TAB, BandagezMod.MODID);

    public static final String EMPTY_BAND_NAME = "empty_band";

    public static final String HEMOSTATIC_BAND_NAME = "hemostatic_band";

    public static final String SMALL_BAND_NAME = "small_band";

    public static final String MEDIUM_BAND_NAME = "medium_band";

    public static final String LARGE_BAND_NAME = "large_band";

    public static final String ANTIBIOTIC_BAND_NAME = "antibiotic_band";

    public static final String MAGIC_BAND_NAME = "magic_band";

    public static final String STIMULANT_BAND_NAME = "stimulant_band";

    public static final String FLORAL_STRING_NAME = "floral_string";

    public static final String SPLINT_NAME = "splint";

    public static final RegistryObject<Item> FLORAL_STRING_ITEM =
            ITEMS.register(FLORAL_STRING_NAME, () -> new Item(new Item.Properties().setId(ITEMS.key(FLORAL_STRING_NAME))));

    public static final RegistryObject<Item> EMPTY_BAND_ITEM = ITEMS.register(EMPTY_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.EMPTY,
                    new Item.Properties().stacksTo(8).setId(ITEMS.key(EMPTY_BAND_NAME))));

    public static final RegistryObject<Item> HEMOSTATIC_BAND_ITEM = ITEMS.register(HEMOSTATIC_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.HEMOSTATIC,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(HEMOSTATIC_BAND_NAME))));

    public static final RegistryObject<Item> SMALL_BAND_ITEM = ITEMS.register(SMALL_BAND_NAME,
            () -> new BandageItem<>(HealingBandageTypes.SMALL,
                    new Item.Properties().stacksTo(16).setId(ITEMS.key(SMALL_BAND_NAME))));

    public static final RegistryObject<Item> MEDIUM_BAND_ITEM = ITEMS.register(MEDIUM_BAND_NAME,
            () -> new BandageItem<>(HealingBandageTypes.MEDIUM,
                    new Item.Properties().stacksTo(8).setId(ITEMS.key(MEDIUM_BAND_NAME))));

    public static final RegistryObject<Item> LARGE_BAND_ITEM = ITEMS.register(LARGE_BAND_NAME,
            () -> new BandageItem<>(HealingBandageTypes.LARGE,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(LARGE_BAND_NAME))));

    public static final RegistryObject<Item> ANTI_BIOTIC_BAND_ITEM = ITEMS.register(ANTIBIOTIC_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.ANTI_BIOTIC,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(ANTIBIOTIC_BAND_NAME))));

    public static final RegistryObject<Item> MAGIC_BAND_ITEM = ITEMS.register(MAGIC_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.MAGIC,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(MAGIC_BAND_NAME))));

    public static final RegistryObject<Item> STIMULANT_BAND_ITEM = ITEMS.register(STIMULANT_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.STIMULANT,
                    new Item.Properties().stacksTo(4).setId(ITEMS.key(STIMULANT_BAND_NAME))));

    public static final RegistryObject<Item> SPLINT_ITEM = ITEMS.register(SPLINT_NAME,
            () -> new SplintItem(new Item.Properties().stacksTo(2).setId(ITEMS.key(SPLINT_NAME))));

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB =
            TAB.register("bandages_tab",
                    () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 7)
                            .title(Component.translatable("bandagez.bandages_tab"))
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
                                output.accept(SPLINT_ITEM.get());
                            })
                            .build());

}
