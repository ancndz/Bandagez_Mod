package ru.ancndz.bandagez.item;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;

public class GrassDropModifier extends LootModifier {

	public static final Supplier<MapCodec<GrassDropModifier>> CODEC = Suppliers
			.memoize(() -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, GrassDropModifier::new)));

	protected GrassDropModifier(LootItemCondition[] conditionsIn) {
		super(conditionsIn);
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
			LootContext context) {
		if (Optional.ofNullable(context.getParamOrNull(LootContextParams.BLOCK_STATE)).map(BlockState::getBlock)
				.map(Blocks.SHORT_GRASS::equals).orElse(Boolean.FALSE)) {
			generatedLoot.add(new ItemStack(Items.FLORAL_STRING_ITEM.get()));
		}
		return generatedLoot;
	}

	@Override
	public MapCodec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}

}
