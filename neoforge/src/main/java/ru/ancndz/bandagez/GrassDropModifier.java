package ru.ancndz.bandagez;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import ru.ancndz.bandagez.loot.CommonGrassDropLogic;

import java.util.function.Supplier;

public class GrassDropModifier extends LootModifier {

    public static final String GRASS_DROP_MODIFIER = "grass_drop_modifier";

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BandagezMod.MODID);

    public static final Supplier<MapCodec<GrassDropModifier>> CODEC_SUPPLIER = Suppliers
            .memoize(() -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, GrassDropModifier::new)));

    protected GrassDropModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        return CommonGrassDropLogic.apply(generatedLoot, lootContext);
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC_SUPPLIER.get();
    }

}
