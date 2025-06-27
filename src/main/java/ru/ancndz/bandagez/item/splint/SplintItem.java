package ru.ancndz.bandagez.item.splint;

import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import ru.ancndz.bandagez.effect.EffectPriority;
import ru.ancndz.bandagez.effect.ModMobEffects;
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
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return 50; // Duration for using the splint
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>
            use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (getRemovingEffects().stream().map(RegistryObject::get).noneMatch(player::hasEffect)) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
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
                .map(RegistryObject::get)
                .filter(effect -> entityLiving.hasEffect(effect) && effect instanceof EffectPriority)
                .min(Comparator.comparing(effect -> ((EffectPriority) effect).getPriority()))
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
    public List<RegistryObject<MobEffect>> getRemovingEffects() {
        return List.of(ModMobEffects.BONE_FRACTURE_ARM_MAIN, ModMobEffects.BONE_FRACTURE_LEG, ModMobEffects.BONE_FRACTURE_ARM);
    }

    @Override
    public void addCustomTooltip(List<Component> components) {
        components.add(Component.translatable("bandagez.tooltip.removing_effects"));
        components.add(Component.translatable("effect.bandagez.fractured").withStyle(ChatFormatting.RED));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack itemStack) {
        return UseAnim.BOW;
    }
}
