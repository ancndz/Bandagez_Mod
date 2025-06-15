package ru.ancndz.bandagez.item;

import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import ru.ancndz.bandagez.mod.BandagezMod;

public class BandItem extends Item {

    private static final Logger LOGGER = LogUtils.getLogger();

    private final int healTime;

    private final int healAmount;

    private final int maxHeal;

    private final int itemUseDuration;

    private final SoundEvent usingSound = BandagezMod.BANDAGE_USE_START.get();

    private final SoundEvent midUsingSound = BandagezMod.BANDAGE_USE_MID.get();

    private final SoundEvent afterUsingSound = BandagezMod.BANDAGE_USE_END.get();

    private boolean startSoundPlayed = false;

    private boolean midSoundPlayed = false;

    public BandItem(int maxStackSize, int healTime, int amount, int maxHP, int duration) {
        super(new Properties().stacksTo(maxStackSize));

        this.healTime = healTime;
        this.healAmount = amount;
        this.maxHeal = maxHP;
        this.itemUseDuration = duration;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack itemstack) {
        return this.itemUseDuration;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack>
            use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player.getHealth() >= this.getMaxHeal()) {
            LOGGER.debug("Player {} ({} hp) tried to use bandage with max heal {}.",
                    player.getName().getString(),
                    player.getHealth(),
                    this.getMaxHeal());
            return InteractionResultHolder.fail(player.getItemInHand(hand));
        }
        LOGGER.debug("Player {} ({} hp) is using bandage with max heal {}.",
                player.getName().getString(),
                player.getHealth(),
                this.getMaxHeal());
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack,
            @NotNull Level worldIn,
            @NotNull LivingEntity entityLiving,
            int count) {

    }

    @Override
    public void onUseTick(@NotNull Level worldIn,
            @NotNull LivingEntity entityLiving,
            @NotNull ItemStack itemStack,
            int count) {
        if (!worldIn.isClientSide()) {
            float f = (float) (itemStack.getUseDuration() - count) / (itemStack.getUseDuration() - 3);
            LOGGER.debug("onUseTick: count = {}, f = {}, startSoundPlayed = {}", count, f, this.startSoundPlayed);

            if (f < 0.15F) {
                this.startSoundPlayed = false;
                this.midSoundPlayed = false;
            } else if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                LOGGER.debug("Playing start sound for player {}.", entityLiving.getName().getString());
                worldIn.playSound(null,
                        entityLiving.getOnPos(),
                        this.usingSound,
                        SoundSource.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
            if (f >= 0.5F && !this.midSoundPlayed) {
                this.midSoundPlayed = true;
                LOGGER.debug("Playing mid using sound for player {}.", entityLiving.getName().getString());
                worldIn.playSound(null,
                        entityLiving.getOnPos(),
                        this.midUsingSound,
                        SoundSource.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    @Override
    public @NotNull ItemStack
            finishUsingItem(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving) {
        final BandItem item = (BandItem) stack.getItem();
        int hp = item.getMaxHeal();

        if (entityLiving instanceof ServerPlayer serverPlayer) {
            LOGGER.debug("Player {} finished using bandage, health before: {}, max heal: {}.",
                    serverPlayer.getName().getString(),
                    entityLiving.getHealth(),
                    hp);
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
        }

        if (entityLiving instanceof Player player && entityLiving.getHealth() < hp) {
            LOGGER.debug("Player {} is healing from {} hp to {} hp with bandage.",
                    player.getName().getString(),
                    player.getHealth(),
                    hp);
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,
                    (int) (hp - player.getHealth()) * item.getHealTime(),
                    item.getHealAmount()));

            worldIn.playSound(null,
                    player.getOnPos(),
                    this.afterUsingSound,
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
        return UseAnim.BRUSH;
    }

    public int getHealTime() {
        return healTime;
    }

    public int getHealAmount() {
        return healAmount;
    }

    public int getMaxHeal() {
        return maxHeal;
    }

    public SoundEvent getUsingSound() {
        return usingSound;
    }

    public SoundEvent getMidUsingSound() {
        return midUsingSound;
    }

    public SoundEvent getAfterUsingSound() {
        return afterUsingSound;
    }
}