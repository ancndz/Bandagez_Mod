package ru.ancndz.bandagez.item;

import static ru.ancndz.bandagez.mod.LocalizationPath.CREATIVE_TAB_NAME;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import ru.ancndz.bandagez.item.bandage.Bandage;
import ru.ancndz.bandagez.item.bandage.BandageTypes;
import ru.ancndz.bandagez.mod.BandagezMod;

public class Items {

	public static final RegistryObject<Item> FLORAL_STRING_ITEM = BandagezMod.ITEMS.register("floral_string",
			() -> new Item(new Item.Properties()));

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

	public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = BandagezMod.CREATIVE_MODE_TABS.register(
			"bandages_tab",
			() -> CreativeModeTab.builder().title(Component.translatable(CREATIVE_TAB_NAME))
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
					}).build());

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
