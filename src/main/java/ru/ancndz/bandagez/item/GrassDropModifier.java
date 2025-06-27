package ru.ancndz.bandagez.item;

import com.google.common.base.Suppliers;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

public class GrassDropModifier extends LootModifier {

    public static final String GRASS_DROP_MODIFIER = "grass_drop_modifier";

    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS =
        DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, BandagezMod.MODID);

    public static final Supplier<GlobalLootModifierSerializer<?>> CODEC =
            Suppliers.memoize(() -> new GlobalLootModifierSerializer<GrassDropModifier>() {

                @Override
                public GrassDropModifier
                        read(ResourceLocation location, JsonObject object, ILootCondition[] ailootcondition) {
                    return new GrassDropModifier(ailootcondition);
                }

                @Override
                public JsonObject write(GrassDropModifier instance) {
                    return makeConditions(instance.conditions);
                }
            });

    protected GrassDropModifier(ILootCondition[] conditionsIn) {
		super(conditionsIn);
	}

    @Nonnull
	@Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(ModItems.FLORAL_STRING_ITEM.get()));
		return generatedLoot;
	}

}
