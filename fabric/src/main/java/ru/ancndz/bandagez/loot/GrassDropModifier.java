package ru.ancndz.bandagez.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import ru.ancndz.bandagez.item.ModItems;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class GrassDropModifier extends FabricBlockLootTableProvider {

    public static final String GRASS_DROP_MODIFIER = "grass_drop_modifier";

    protected GrassDropModifier(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        Stream.of(Blocks.SHORT_GRASS, Blocks.FERN, Blocks.LARGE_FERN, Blocks.TALL_GRASS)
                .forEach(block -> add(block,
                        block1 -> this.createGrassDrops(block1)
                                .withPool(LootPool.lootPool()
                                        .add(LootItem.lootTableItem(ModItems.FLORAL_STRING_ITEM.get())
                                                .when(LootItemRandomChanceCondition.randomChance(0.05f))
                                                .apply(ApplyBonusCount.addUniformBonusCount(
                                                    Enchantments.FORTUNE))))));
    }

}