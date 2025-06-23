package ru.ancndz.bandagez.item.splint;

import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import ru.ancndz.bandagez.effect.EffectPriority;
import ru.ancndz.bandagez.effect.Effects;
import ru.ancndz.bandagez.item.RemovingEffects;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SplintItem extends Item implements RemovingEffects {

    private static final Logger LOGGER = LogUtils.getLogger();

    public SplintItem(Properties itemProperties) {
        super(itemProperties);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 50; // Duration for using the splint
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>
            use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (getRemovingEffects().stream().noneMatch(player::hasEffect)) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Player {} ({} effects) is using splint",
                    player.getName().getString(),
                    getRemovingEffects().stream()
                            .filter(player::hasEffect)
                            .map(Objects::toString)
                            .collect(Collectors.joining(", ")));
        }
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
                .filter(entityLiving::hasEffect)
                .filter(EffectPriority.class::isInstance)
                .sorted()
                .findFirst()
                .ifPresent(effect -> {
                    entityLiving.removeEffect(effect);
                    LOGGER.debug("Removed effect {} from {}", effect, entityLiving.getName().getString());
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
    public List<MobEffect> getRemovingEffects() {
        return List.of(Effects.BONE_BREAK_ARM_MAIN.get(), Effects.BONE_BREAK_LEG.get(), Effects.BONE_BREAK_ARM.get());
    }
}
