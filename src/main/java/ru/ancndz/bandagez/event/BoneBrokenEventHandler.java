package ru.ancndz.bandagez.event;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.eventbus.internal.Event;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import ru.ancndz.bandagez.effect.Effects;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = BandagezMod.MODID)
public class BoneBrokenEventHandler {

    private static final Logger LOG = LogUtils.getLogger();

    public static final ResourceLocation BROKEN_BONE_ATTACK_DAMAGE =
            ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, "broken_bone_attack_damage");

    @SubscribeEvent
    static void onPlayerFall(LivingFallEvent event) {
        final LivingEntity entity = event.getEntity();
        if (entity.hasEffect(Effects.BONE_BREAK_LEG.getHolder().orElseThrow())) {
            event.setDamageMultiplier(event.getDamageMultiplier() + 0.5F);
        }
    }

    @SubscribeEvent
    static void onPlayerTakesFallDamage(LivingDamageEvent event) {
        final DamageSource source = event.getSource();
        if (!source.is(DamageTypes.FALL)) {
            return;
        }
        final LivingEntity entity = event.getEntity();
        if (event.getAmount() > 10F || event.getEntity().getRandom().nextDouble() < 0.2D) {
            entity.addEffect(new MobEffectInstance(Effects.BONE_BREAK_LEG.getHolder()
                    .orElseThrow(), MobEffectInstance.INFINITE_DURATION, 0, false, false, true));
            LOG.debug("LivingEntity {} has broken leg from fall damage", entity.getName().getString());
        }
    }

    @SubscribeEvent
    static void onPlayerAttacks(AttackEntityEvent event) {
        final Player player = event.getEntity();
        Optional.ofNullable(player.getAttribute(Attributes.ATTACK_DAMAGE)).ifPresent(attributeInstance -> {
            if (player.hasEffect(Effects.BONE_BREAK_ARM_MAIN.getHolder().orElseThrow())) {
                attributeInstance.addOrUpdateTransientModifier(new AttributeModifier(BROKEN_BONE_ATTACK_DAMAGE,
                        -0.4F,
                        AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                LOG.debug("Player {} has broken arm, reducing attack damage. Current attack damage: {}",
                        player.getName().getString(),
                        attributeInstance.getValue());
            } else if (attributeInstance.hasModifier(BROKEN_BONE_ATTACK_DAMAGE)) {
                attributeInstance.removeModifier(BROKEN_BONE_ATTACK_DAMAGE);
                LOG.debug("Player {} does not have broken arm, restoring attack damage", player.getName().getString());
            }
        });
    }

    @SubscribeEvent
    static boolean onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand().equals(InteractionHand.OFF_HAND)
                && event.getEntity().hasEffect(Effects.BONE_BREAK_ARM.getHolder().orElseThrow())) {
            LOG.debug("Player {} has broken arm, canceling right-click action",
                    event.getEntity().getName().getString());
            return true;
        } else {
            return false;
        }
    }

}
