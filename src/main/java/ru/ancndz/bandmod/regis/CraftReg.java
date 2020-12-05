package ru.ancndz.bandmod.regis;

import ru.ancndz.bandmod.mod.MainClassMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;

public class CraftReg {
	
	public static void register()
    {
        registerRecipes("small_band");
        registerRecipes("medium_band");
        registerRecipes("large_band");
    }

    private static void registerRecipes(String name)
    {
        CraftingHelper.register(new ResourceLocation(MainClassMod.MODID, name), (IRecipeFactory) (context, json) -> CraftingHelper.getRecipe(json, context));
    }
}

