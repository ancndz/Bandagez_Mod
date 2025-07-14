package ru.ancndz.bandagez.loot;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import ru.ancndz.bandagez.item.ModItems;

public class CommonGrassDropLogic {

    public static ObjectArrayList<ItemStack>
            apply(LootTable table, ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(ModItems.FLORAL_STRING_ITEM.get()));
        return generatedLoot;
    }

    public static ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        return apply(null, generatedLoot, context);
    }

    public static ObjectArrayList<ItemStack> apply(ObjectArrayList<ItemStack> generatedLoot) {
        return apply(generatedLoot, null);
    }
}
