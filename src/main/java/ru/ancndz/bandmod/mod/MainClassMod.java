package ru.ancndz.bandmod.mod;

import ru.ancndz.bandmod.proxy.CommonProxy;
import ru.ancndz.bandmod.tabs.TabMain;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = MainClassMod.MODID, version = MainClassMod.VERSION, name = "Bandagez Mod")
public class MainClassMod
{
    public static final String MODID = "bandmod";
    public static final String VERSION = "0.0.1";

    @SidedProxy(clientSide = "ru.ancndz.bandmod.proxy.ClientProxy", serverSide = "ru.ancndz.bandmod.proxy.CommonProxy")
    public static CommonProxy proxy;

    //tab
    public static CreativeTabs tabMain = new TabMain("Bandagez");
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) { proxy.preInit(event); }

    @EventHandler
    public void init(FMLInitializationEvent event) { proxy.init(event); }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) { proxy.postInit(event); }
}