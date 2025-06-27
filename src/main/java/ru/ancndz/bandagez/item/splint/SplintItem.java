package ru.ancndz.bandagez.item.splint;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effect;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ancndz.bandagez.effect.EffectPriority;
import ru.ancndz.bandagez.effect.ModEffects;
import ru.ancndz.bandagez.item.RemovingEffects;
import ru.ancndz.bandagez.item.SupplyCustomTooltip;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SplintItem extends Item implements RemovingEffects, SupplyCustomTooltip {

    private static final Logger LOGGER = LoggerFactory.getLogger(SplintItem.class);

    public SplintItem(Item.Properties itemProperties) {
        super(itemProperties);
        LOGGER.debug("Created splint.");
    }

    @Override
    public int getUseDuration(ItemStack itemstack) {
        return 50; // Duration for using the splint
    }

    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        if (getRemovingEffects().stream().map(RegistryObject::get).noneMatch(player::hasEffect)) {
            return ActionResult.fail(player.getItemInHand(hand));
        }
        LOGGER.debug("Player {} ({} hp) is using splint", player.getName().getString(), player.getHealth());
        player.startUsingItem(hand);
        return ActionResult.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World level, LivingEntity entityLiving) {
        if (entityLiving instanceof ServerPlayerEntity) {
            LOGGER.debug("Player {} finished using splint.", entityLiving.getName().getString());
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity) entityLiving, stack);
        }

        getRemovingEffects().stream()
                .map(RegistryObject::get)
                .filter(effect -> entityLiving.hasEffect(effect) && effect instanceof EffectPriority)
                .min(Comparator.comparing(effect -> ((EffectPriority) effect).getPriority()))
                .ifPresent(effect -> {
                    entityLiving.removeEffect(effect);
                    LOGGER.debug("Removed effect {} from {}", effect, entityLiving.getName().getString());
                });

        if (entityLiving instanceof PlayerEntity) {
            ((PlayerEntity) entityLiving).awardStat(Stats.ITEM_USED.get(this));
            if (!((PlayerEntity) entityLiving).abilities.instabuild) {
                stack.shrink(1);
            }
        }
        return stack;
    }

    @Override
    public List<RegistryObject<Effect>> getRemovingEffects() {
        return Arrays
                .asList(ModEffects.BONE_FRACTURE_ARM_MAIN, ModEffects.BONE_FRACTURE_LEG, ModEffects.BONE_FRACTURE_ARM);
    }

    @Override
    public void addCustomTooltip(List<ITextComponent> components) {
        components.add(new TranslationTextComponent("bandagez.tooltip.removing_effects"));
        components.add(new TranslationTextComponent("effect.bandagez.fractured").withStyle(TextFormatting.RED));
    }

    @Override
    public UseAction getUseAnimation(ItemStack itemStack) {
        return UseAction.BOW;
    }
}
