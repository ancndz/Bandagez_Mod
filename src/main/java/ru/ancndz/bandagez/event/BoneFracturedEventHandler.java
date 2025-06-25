package ru.ancndz.bandagez.event;

import static java.util.Map.entry;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber(modid = BandagezMod.MODID)
public class BoneFracturedEventHandler {

    private static final Logger LOG = LogUtils.getLogger();

    private static final Map<ResourceKey<DamageType>, Double> DAMAGE_TYPES_FOR_BONE_FRACTURE =
            Map.ofEntries(entry(DamageTypes.EXPLOSION, 0.2D), entry(DamageTypes.FALLING_STALACTITE, 0.2D));

    @SuppressWarnings("rawtypes")
    private static final Map<EntityType,
            Double> ENTITY_TYPES_FOR_BONE_FRACTURE = Map.ofEntries(entry(EntityType.ZOMBIE, 0.1D),
                    entry(EntityType.ZOMBIFIED_PIGLIN, 0.3D),
                    entry(EntityType.POLAR_BEAR, 0.3D),
                    entry(EntityType.WITHER_SKELETON, 0.2D));

    @SubscribeEvent
    static void onPlayerFall(LivingFallEvent event) {
        final LivingEntity entity = event.getEntity();
        if (entity.hasEffect(Effects.BONE_FRACTURE_LEG.getHolder().orElseThrow())) {
            event.setDamageMultiplier(event.getDamageMultiplier() + 0.5F);
        }
    }

    @SubscribeEvent
    static void onPlayerTakesFallDamage(LivingDamageEvent event) {
        final DamageSource source = event.getSource();
        final LivingEntity entity = event.getEntity();
        if (!source.is(DamageTypes.FALL) || !(entity instanceof Player player)) {
            return;
        }
        if (event.getAmount() > 10F || player.getRandom().nextDouble() < 0.2D) {
            player.addEffect(new MobEffectInstance(Effects.BONE_FRACTURE_LEG.getHolder()
                    .orElseThrow(), MobEffectInstance.INFINITE_DURATION, 0, false, false, true));
            LOG.debug("LivingEntity {} has broken leg from fall damage", player.getName().getString());
        }
    }

    @SubscribeEvent
    static void onPlayerTakesDamage(LivingDamageEvent event) {
        final DamageSource source = event.getSource();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        DAMAGE_TYPES_FOR_BONE_FRACTURE.keySet()
                .stream()
                .filter(source::is)
                .findFirst()
                .ifPresent(type -> atomicBoolean
                        .set(applyEffect(event.getEntity(), DAMAGE_TYPES_FOR_BONE_FRACTURE.get(type), source)));
        if (atomicBoolean.get()) {
            return;
        }

        final Entity damageSourceEntity = source.getEntity();
        if (damageSourceEntity == null || !damageSourceEntity.isAlive()) {
            return;
        }
        Optional.ofNullable(ENTITY_TYPES_FOR_BONE_FRACTURE.get(damageSourceEntity.getType()))
                .ifPresent(bleedingChance -> atomicBoolean.set(applyEffect(event.getEntity(), bleedingChance, source)));
    }

    private static boolean applyEffect(LivingEntity entity, Double bleedingChance, DamageSource source) {
        if (entity.level().getRandom().nextFloat() < bleedingChance) {
            if (entity.level().getRandom().nextFloat() < bleedingChance / 2) {
                entity.addEffect(
                        new MobEffectInstance(Effects.BONE_FRACTURE_ARM_MAIN.getHolder().orElseThrow(),
                                MobEffectInstance.INFINITE_DURATION),
                        source.getEntity());
            } else {
                entity.addEffect(
                        new MobEffectInstance(Effects.BONE_FRACTURE_ARM.getHolder().orElseThrow(),
                                MobEffectInstance.INFINITE_DURATION),
                        source.getEntity());
            }
            return true;
        }
        return false;
    }

    @SubscribeEvent
    static boolean onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand().equals(InteractionHand.OFF_HAND)
                && event.getEntity().hasEffect(Effects.BONE_FRACTURE_ARM.getHolder().orElseThrow())) {
            LOG.debug("Player {} has broken arm, canceling right-click action",
                    event.getEntity().getName().getString());
            return true;
        } else {
            return false;
        }
    }

}
