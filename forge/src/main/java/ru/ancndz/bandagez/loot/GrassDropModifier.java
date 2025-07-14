package ru.ancndz.bandagez.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ru.ancndz.bandagez.BandagezMod;

import java.util.function.Supplier;

public class GrassDropModifier extends LootModifier {

    public static final String GRASS_DROP_MODIFIER = "grass_drop_modifier";

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, BandagezMod.MODID);

    public static final Supplier<Codec<GrassDropModifier>> CODEC_SUPPLIER = Suppliers
        .memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, GrassDropModifier::new)));

    protected GrassDropModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        return CommonGrassDropLogic.apply(generatedLoot, lootContext);
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC_SUPPLIER.get();
    }

}
