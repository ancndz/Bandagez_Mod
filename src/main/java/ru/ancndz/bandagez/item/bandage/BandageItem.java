package ru.ancndz.bandagez.item.bandage;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ancndz.bandagez.item.Typed;
import ru.ancndz.bandagez.sound.ModSoundEvent;

public class BandageItem<T extends BandageType> extends Item implements Typed<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandageItem.class);

    private final T bandageType;

    private boolean startSoundPlayed = false;

    private boolean midSoundPlayed = false;

    private boolean midLateSoundPlayed = false;

    public BandageItem(T bandageType, Item.Properties properties) {
        super(properties);
        this.bandageType = bandageType;
        LOGGER.debug("Created bandage with type {}", bandageType.getName());
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return bandageType.getUseDuration();
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        if (!bandageType.canUse(player)) {
            return ActionResult.fail(player.getItemInHand(hand));
        }
        LOGGER.debug("Player {} ({} hp) is using bandage", player.getName().getString(), player.getHealth());
        player.startUsingItem(hand);
        return ActionResult.consume(player.getItemInHand(hand));
    }

    @Override
    public void onUseTick(World worldIn, LivingEntity entityLiving, ItemStack itemStack,
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
                        entityLiving.getX(),
                        entityLiving.getY(),
                        entityLiving.getZ(),
                        ModSoundEvent.BANDAGE_USE_START.get(),
                        SoundCategory.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
            if (f >= 0.2F && !this.midSoundPlayed) {
                LOGGER.debug("Start playing midSound, f = {}", f);
                this.midSoundPlayed = true;
                worldIn.playSound(null,
                        entityLiving.getX(),
                        entityLiving.getY(),
                        entityLiving.getZ(),
                        ModSoundEvent.BANDAGE_USE_MID.get(),
                        SoundCategory.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
            if (f >= 0.5F && !this.midLateSoundPlayed) {
                LOGGER.debug("Start playing midLateSound, f = {}", f);
                this.midLateSoundPlayed = true;
                worldIn.playSound(null,
                        entityLiving.getX(),
                        entityLiving.getY(),
                        entityLiving.getZ(),
                        ModSoundEvent.MEDIUM_BANDAGE_USE.get(),
                        SoundCategory.PLAYERS,
                        0.5F,
                        worldIn.getRandom().nextFloat() * 0.1F + 0.9F);
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        if (entityLiving instanceof ServerPlayerEntity) {
            LOGGER.debug("Player {} finished using bandage, health before: {}.",
                    entityLiving.getName().getString(),
					entityLiving.getHealth());
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) entityLiving, stack);
		}

        LOGGER.debug("Player {} applying bandage {}.", entityLiving.getName().getString(), bandageType.getName());
        bandageType.removeEffects(entityLiving);
        bandageType.apply(entityLiving);

        worldIn.playSound(null,
                entityLiving.getX(),
                entityLiving.getY(),
                entityLiving.getZ(),
                ModSoundEvent.BANDAGE_USE_END.get(),
                SoundCategory.PLAYERS,
                0.5F,
                worldIn.getRandom().nextFloat() * 0.1F + 0.9F);

        if (entityLiving instanceof PlayerEntity) {
            ((PlayerEntity) entityLiving).awardStat(Stats.ITEM_USED.get(this));
            if (!((PlayerEntity) entityLiving).abilities.instabuild) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public T getType() {
        return bandageType;
    }
}