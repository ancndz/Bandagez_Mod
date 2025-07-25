package ru.ancndz.bandagez.item.splint;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import ru.ancndz.bandagez.effect.EffectPriority;
import ru.ancndz.bandagez.effect.Effects;
import ru.ancndz.bandagez.item.RemovingEffects;
import ru.ancndz.bandagez.item.SupplyCustomTooltip;

import java.util.Comparator;
import java.util.List;

public class SplintItem extends Item implements RemovingEffects, SupplyCustomTooltip {

    private static final Logger LOGGER = LogUtils.getLogger();

    public SplintItem(Properties itemProperties) {
        super(itemProperties);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack, @NotNull LivingEntity entity) {
        return 50; // Duration for using the splint
    }

    @Override
    public @NotNull InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (getRemovingEffects().stream().noneMatch(player::hasEffect)) {
            return InteractionResult.FAIL;
        }
        LOGGER.debug("Player {} ({} hp) is using splint", player.getName().getString(), player.getHealth());
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public @NotNull ItemStack
            finishUsingItem(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving) {
        if (entityLiving instanceof ServerPlayer serverPlayer) {
            LOGGER.debug("Player {} finished using splint.", serverPlayer.getName().getString());
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        getRemovingEffects().stream()
                .filter(holder -> entityLiving.hasEffect(holder) && holder.get() instanceof EffectPriority)
                .min(Comparator.comparing(mobEffectHolder -> ((EffectPriority) mobEffectHolder.get()).getPriority()))
                .ifPresent(effectHolder -> {
                    entityLiving.removeEffect(effectHolder);
                    LOGGER.debug("Removed effect {} from {}", effectHolder, entityLiving.getName().getString());
                });

        if (entityLiving instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    @Override
    public List<Holder<MobEffect>> getRemovingEffects() {
        return List.of(Effects.BONE_FRACTURE_ARM_MAIN.getHolder().orElseThrow(),
                Effects.BONE_FRACTURE_LEG.getHolder().orElseThrow(),
                Effects.BONE_FRACTURE_ARM.getHolder().orElseThrow());
    }

    @Override
    public void addCustomTooltip(List<Component> components) {
        components.add(Component.translatable("bandagez.tooltip.removing_effects"));
        components.add(Component.translatable("effect.bandagez.fractured").withStyle(ChatFormatting.RED));
    }

    @Override
    public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack itemStack) {
        return ItemUseAnimation.BOW;
    }
}
