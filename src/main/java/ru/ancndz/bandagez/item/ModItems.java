package ru.ancndz.bandagez.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.ancndz.bandagez.item.bandage.BandageItem;
import ru.ancndz.bandagez.item.bandage.BandageTypes;
import ru.ancndz.bandagez.item.bandage.HealingBandageTypes;
import ru.ancndz.bandagez.item.splint.SplintItem;
import ru.ancndz.bandagez.mod.BandagezMod;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BandagezMod.MODID);

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

    public static final ItemGroup BAND_GROUP = (new ItemGroup(BandagezMod.MODID) {

        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return SMALL_BAND_ITEM.get().getDefaultInstance();
        }
    }).setRecipeFolderName(BandagezMod.MODID);

    public static final RegistryObject<Item> FLORAL_STRING_ITEM =
            ITEMS.register(FLORAL_STRING_NAME, () -> new Item(new Item.Properties().tab(BAND_GROUP)));

    public static final RegistryObject<Item> EMPTY_BAND_ITEM = ITEMS.register(EMPTY_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.EMPTY, new Item.Properties().stacksTo(8).tab(BAND_GROUP)));

    public static final RegistryObject<Item> HEMOSTATIC_BAND_ITEM = ITEMS.register(HEMOSTATIC_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.HEMOSTATIC, new Item.Properties().stacksTo(4).tab(BAND_GROUP)));

    public static final RegistryObject<Item> SMALL_BAND_ITEM = ITEMS.register(SMALL_BAND_NAME,
            () -> new BandageItem<>(HealingBandageTypes.SMALL, new Item.Properties().stacksTo(16).tab(BAND_GROUP)));

    public static final RegistryObject<Item> MEDIUM_BAND_ITEM = ITEMS.register(MEDIUM_BAND_NAME,
            () -> new BandageItem<>(HealingBandageTypes.MEDIUM, new Item.Properties().stacksTo(8).tab(BAND_GROUP)));

    public static final RegistryObject<Item> LARGE_BAND_ITEM = ITEMS.register(LARGE_BAND_NAME,
            () -> new BandageItem<>(HealingBandageTypes.LARGE, new Item.Properties().stacksTo(4).tab(BAND_GROUP)));

    public static final RegistryObject<Item> ANTI_BIOTIC_BAND_ITEM = ITEMS.register(ANTIBIOTIC_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.ANTI_BIOTIC, new Item.Properties().stacksTo(4).tab(BAND_GROUP)));

    public static final RegistryObject<Item> MAGIC_BAND_ITEM = ITEMS.register(MAGIC_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.MAGIC, new Item.Properties().stacksTo(4).tab(BAND_GROUP)));

    public static final RegistryObject<Item> STIMULANT_BAND_ITEM = ITEMS.register(STIMULANT_BAND_NAME,
            () -> new BandageItem<>(BandageTypes.STIMULANT, new Item.Properties().stacksTo(4).tab(BAND_GROUP)));

    public static final RegistryObject<Item> SPLINT_ITEM =
            ITEMS.register(SPLINT_NAME, () -> new SplintItem(new Item.Properties().stacksTo(2).tab(BAND_GROUP)));

    private ModItems() {
    }
}
