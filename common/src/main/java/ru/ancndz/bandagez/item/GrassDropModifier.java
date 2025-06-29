package ru.ancndz.bandagez.item;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.function.Supplier;

public class GrassDropModifier extends LootModifier {

	public static final String GRASS_DROP_MODIFIER = "grass_drop_modifier";

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BandagezMod.MODID);

    public static final Supplier<MapCodec<GrassDropModifier>> CODEC_SUPPLIER = Suppliers
			.memoize(() -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, GrassDropModifier::new)));

	protected GrassDropModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(LootTable table, ObjectArrayList<ItemStack> generatedLoot,
			LootContext context) {
        generatedLoot.add(new ItemStack(ModItems.FLORAL_STRING_ITEM.get()));
		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CODEC_SUPPLIER.get();
	}

}
