package ru.ancndz.bandagez.event;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
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
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import ru.ancndz.bandagez.effect.Effects;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = BandagezMod.MODID)
public class BoneBrokenEventHandler {

    private static final Logger LOG = LogUtils.getLogger();

    @SubscribeEvent
    static void onPlayerFall(LivingFallEvent event) {
        final LivingEntity entity = event.getEntity();
        if (entity.hasEffect(Effects.BONE_BREAK_LEG.get())) {
            event.setDamageMultiplier(event.getDamageMultiplier() + 0.5F);
        }
    }

    @SubscribeEvent
    static void onPlayerTakesFallDamage(LivingDamageEvent event) {
        final DamageSource source = event.getSource();
        if (!source.is(DamageTypes.FALL) || !(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }
        if (event.getAmount() > 10F || event.getEntity().getRandom().nextDouble() < 0.2D) {
            player.addEffect(new MobEffectInstance(Effects.BONE_BREAK_LEG
                    .get(), MobEffectInstance.INFINITE_DURATION, 0, false, false, true));
            LOG.debug("LivingEntity {} has broken leg from fall damage", player.getName().getString());
        }
    }

    @SubscribeEvent
    static void onPlayerAttacks(AttackEntityEvent event) {
        final Player player = event.getEntity();
        if (!(player instanceof ServerPlayer)) {
            return;
        }
        Optional.ofNullable(player.getAttribute(Attributes.ATTACK_DAMAGE)).ifPresent(attributeInstance -> {
            AttributeModifier attributeModifier = new AttributeModifier("broken_bone_attack_damage",
                    -0.4F,
                    AttributeModifier.Operation.MULTIPLY_BASE);
            if (player.hasEffect(Effects.BONE_BREAK_ARM_MAIN.get())
                    && !attributeInstance.hasModifier(attributeModifier)) {
                attributeInstance.addTransientModifier(attributeModifier);
                LOG.debug("Player {} has broken arm, reducing attack damage. Current attack damage: {}",
                        player.getName().getString(),
                        attributeInstance.getValue());
            } else if (attributeInstance.hasModifier(attributeModifier)) {
                attributeInstance.removeModifier(attributeModifier);
                LOG.debug("Player {} does not have broken arm, restoring attack damage", player.getName().getString());
            }
        });
    }

    @SubscribeEvent
    static boolean onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand().equals(InteractionHand.OFF_HAND)
                && event.getEntity().hasEffect(Effects.BONE_BREAK_ARM.get())) {
            LOG.debug("Player {} has broken arm, canceling right-click action",
                    event.getEntity().getName().getString());
            return true;
        } else {
            return false;
        }
    }

}
