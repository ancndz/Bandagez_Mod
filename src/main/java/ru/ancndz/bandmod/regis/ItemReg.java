package ru.ancndz.bandmod.regis;


import ru.ancndz.bandmod.item.ItemBand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemReg {

	
	public static Item small_band = new ItemBand("small_band", 16, 50, 0, 10, 40);
    public static Item medium_band = new ItemBand("medium_band", 8, 25, 1, 15, 80);
    public static Item large_band = new ItemBand("large_band", 4, 12, 2, 20, 120);

    
    public static void register()
    {
        setRegister(small_band);
        setRegister(medium_band);
        setRegister(large_band);
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender()
    {
        setRender(small_band);
        setRender(medium_band);
        setRender(large_band);
       // setRender(modpack_img);
    }

    private static void setRegister(Item item)
    {
        ForgeRegistries.ITEMS.register(item);
    }

    @SideOnly(Side.CLIENT)
    private static void setRender(Item item)
    {
    	Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}