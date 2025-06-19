package ru.ancndz.bandagez.item.bandage;

import static ru.ancndz.bandagez.mod.LocalizationPath.BANDAGE_TOOLTIP_REMOVING_EFFECTS;

import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import ru.ancndz.bandagez.sound.Sounds;

import java.util.function.Consumer;

public class BandageItem extends Item {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final BandageType bandageType;

    private boolean startSoundPlayed = false;

    private boolean midSoundPlayed = false;

    private boolean midLateSoundPlayed = false;

	public BandageItem(BandageType bandageType, Properties properties) {
        super(properties);
        this.bandageType = bandageType;
    }

    @Override
	public int getUseDuration(@NotNull ItemStack itemstack, @NotNull LivingEntity entity) {
        return bandageType.getUseDuration();
    }

    @Override
	@Deprecated
	public void appendHoverText(@NotNull ItemStack itemStack, @NotNull TooltipContext tooltipContext,
			@NotNull TooltipDisplay tooltipDisplay, Consumer<Component> componentConsumer, TooltipFlag tooltipFlag) {
		if (bandageType == BandageTypes.SMALL || bandageType == BandageTypes.MEDIUM
				|| bandageType == BandageTypes.LARGE) {
			componentConsumer.accept(Component.translatable("bandagez.tooltip.bandage_healing"));
		}
        if (!bandageType.getRemovingEffects().isEmpty()) {
			componentConsumer.accept(Component.translatable(BANDAGE_TOOLTIP_REMOVING_EFFECTS));
            for (var effect : bandageType.getRemovingEffects()) {
				final MobEffect mobEffect = effect.get();
				componentConsumer.accept(Component.translatable(mobEffect.getDescriptionId())
						.withStyle(mobEffect.getCategory().getTooltipFormatting()));
            }
        }
    }

    @Override
	public @NotNull InteractionResult
            use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!bandageType.canUse(player)) {
			return InteractionResult.FAIL;
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
			float f = (float) (itemStack.getUseDuration(entityLiving) - count)
					/ (itemStack.getUseDuration(entityLiving) - 12);
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
						Sounds.BANDAGE_USE_START.get(),
                        SoundSource.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
            if (f >= 0.2F && !this.midSoundPlayed) {
                LOGGER.debug("Start playing midSound, f = {}", f);
                this.midSoundPlayed = true;
                worldIn.playSound(null,
                        entityLiving.getOnPos(),
						Sounds.BANDAGE_USE_MID.get(),
                        SoundSource.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
            if (f >= 0.5F && !this.midLateSoundPlayed) {
                LOGGER.debug("Start playing midLateSound, f = {}", f);
                this.midLateSoundPlayed = true;
                worldIn.playSound(null,
                        entityLiving.getOnPos(),
						Sounds.MEDIUM_BANDAGE_USE.get(),
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
			LOGGER.debug("Player {} finished using bandage, health before: {}.", serverPlayer.getName().getString(),
					entityLiving.getHealth());
			CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
		}

		LOGGER.debug("Player {} applying bandage {}.", entityLiving.getName().getString(), bandageType.getName());
		bandageType.applyEffects(entityLiving);

		worldIn.playSound(null, entityLiving.getOnPos(), Sounds.BANDAGE_USE_END.get(), SoundSource.PLAYERS, 0.5F,
				worldIn.getRandom().nextFloat() * 0.1F + 0.9F);

		if (entityLiving instanceof Player player) {
			player.awardStat(Stats.ITEM_USED.get(this));
			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
        }
        return stack;
    }

    @Override
	public @NotNull ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
		return ItemUseAnimation.NONE;
    }

}