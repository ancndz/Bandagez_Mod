package ru.ancndz.bandmod.tabs;

import ru.ancndz.bandmod.regis.ItemReg;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabMain extends CreativeTabs {
	
	public TabMain(String label) {
		super(label);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ItemReg.small_band);
	}
}
