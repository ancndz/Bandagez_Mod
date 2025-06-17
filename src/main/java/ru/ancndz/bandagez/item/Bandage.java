package ru.ancndz.bandagez.item;

import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.List;

public class Bandage extends Item {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final BandageType bandageType;

    private boolean startSoundPlayed = false;

    private boolean midSoundPlayed = false;

    private boolean midLateSoundPlayed = false;

    public Bandage(BandageType bandageType, Properties properties) {
        super(properties);
        this.bandageType = bandageType;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return bandageType.getUseDuration();
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack,
            @Nullable Level level,
            @NotNull List<Component> mainComponent,
            @NotNull TooltipFlag flag) {
        if (!bandageType.getRemovingEffects().isEmpty()) {
            mainComponent.add(Component.translatable("bandagez.item.bandage.tooltip.removing_effects",
                    bandageType.getRemovingEffects().size()));
            for (var effect : bandageType.getRemovingEffects()) {
                mainComponent.add(Component.translatable(effect.getDescriptionId()));
            }
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>
            use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!bandageType.canUse(player)) {
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        LOGGER.debug("Player {} ({} hp) is using bandage", player.getName().getString(), player.getHealth());
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public void onUseTick(@NotNull Level worldIn,
            @NotNull LivingEntity entityLiving,
            @NotNull ItemStack itemStack,
            int count) {
        if (!worldIn.isClientSide()) {
            float f = (float) (itemStack.getUseDuration() - count) / (itemStack.getUseDuration() - 12);
            LOGGER.debug("onUseTick: count = {}, f = {}", count, f);

            if (f < 0.05F) {
                this.startSoundPlayed = false;
                this.midSoundPlayed = false;
            }
            if (f >= 0.05F && !this.startSoundPlayed) {
                LOGGER.debug("Start playing startSound, f = {}", f);
                this.startSoundPlayed = true;
                worldIn.playSound(null,
                        entityLiving.getOnPos(),
                        BandagezMod.BANDAGE_USE_START.get(),
                        SoundSource.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
            if (f >= 0.2F && !this.midSoundPlayed) {
                LOGGER.debug("Start playing midSound, f = {}", f);
                this.midSoundPlayed = true;
                worldIn.playSound(null,
                        entityLiving.getOnPos(),
                        BandagezMod.BANDAGE_USE_MID.get(),
                        SoundSource.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
            if (f >= 0.5F && !this.midLateSoundPlayed) {
                LOGGER.debug("Start playing midLateSound, f = {}", f);
                this.midLateSoundPlayed = true;
                worldIn.playSound(null,
                        entityLiving.getOnPos(),
                        BandagezMod.MEDIUM_BANDAGE_USE.get(),
                        SoundSource.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    @Override
    public @NotNull ItemStack
            finishUsingItem(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving) {
        if (entityLiving instanceof ServerPlayer serverPlayer) {
            LOGGER.debug("Player {} finished using bandage, health before: {}.",
                    serverPlayer.getName().getString(),
                    entityLiving.getHealth());
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        if (entityLiving instanceof Player player) {
            LOGGER.debug("Player {} is healing from {} hp with bandage.",
                    player.getName().getString(),
                    player.getHealth());

            bandageType.applyEffects(player);

            worldIn.playSound(null,
                    player.getOnPos(),
                    BandagezMod.BANDAGE_USE_END.get(),
                    SoundSource.PLAYERS,
                    0.5F,
                    worldIn.getRandom().nextFloat() * 0.1F + 0.9F);

            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }

}