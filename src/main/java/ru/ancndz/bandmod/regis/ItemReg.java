package ru.ancndz.bandmod.regis;


import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import ru.ancndz.bandmod.item.ItemBand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@GameRegistry.ObjectHolder("bandmod")
@Mod.EventBusSubscriber
public class ItemReg {
	
	public static Item small_band = new ItemBand("small_band", 16, 50, 0, 10, 40);
    public static Item medium_band = new ItemBand("medium_band", 8, 25, 1, 15, 80);
    public static Item large_band = new ItemBand("large_band", 4, 12, 2, 20, 120);


    @SubscribeEvent
    public static void onRegistryItem(RegistryEvent.Register<Item> e) {
        e.getRegistry().registerAll(small_band, medium_band, large_band);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRegistryModel(ModelRegistryEvent e) {
        registryModel(small_band);
        registryModel(medium_band);
        registryModel(large_band);
    }

    @SideOnly(Side.CLIENT)
    private static void registryModel(Item item) {
        final ModelResourceLocation location = new ModelResourceLocation(item.getRegistryName().toString());
        ModelBakery.registerItemVariants(item, location);// Регистрация вариантов предмета. Это нужно если мы хотим использовать подтипы предметов/блоков(см. статью подтипы)
        ModelLoader.setCustomModelResourceLocation(item, 0, location);// Устанавливаем вариант модели для нашего предмета. Без регистрации варианта модели, сама модель не будет установлена для предмета/блока(см. статью подтипы)
    }
}