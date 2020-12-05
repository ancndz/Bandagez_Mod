package ru.ancndz.bandmod.proxy;

import ru.ancndz.bandmod.regis.CraftReg;
import ru.ancndz.bandmod.regis.ItemReg;
import ru.ancndz.bandmod.regis.SoundHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event)
    {
		ItemReg.register();
		//eventshere
		
    }

    public void init(FMLInitializationEvent event)
    {
    	SoundHandler.registerSounds();
    	CraftReg.register();
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
