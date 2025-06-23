package ru.ancndz.bandagez.event;

import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import ru.ancndz.bandagez.effect.Effects;
import ru.ancndz.bandagez.mod.BandagezMod;

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
        if (!source.is(DamageTypes.FALL) || !(event.getEntity() instanceof Player player)) {
            return;
        }
        if (event.getAmount() > 10F || event.getEntity().getRandom().nextDouble() < 0.2D) {
            player.addEffect(new MobEffectInstance(Effects.BONE_BREAK_LEG
                    .get(), MobEffectInstance.INFINITE_DURATION, 0, false, false, true));
            LOG.debug("LivingEntity {} has broken leg from fall damage", player.getName().getString());
        }
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
