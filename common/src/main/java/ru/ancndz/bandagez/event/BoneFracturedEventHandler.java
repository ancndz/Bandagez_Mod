package ru.ancndz.bandagez.event;

import static java.util.Map.entry;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import ru.ancndz.bandagez.effect.ModMobEffects;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

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

    public static float onPlayerFall(LivingEntity entity) {
        if (entity.hasEffect(ModMobEffects.BONE_FRACTURE_LEG.getHolder())) {
            return 0.5F;
        } else
            return 0;
    }

    public static void onPlayerTakesFallDamage(DamageSource source, LivingEntity entity, float amount) {
        if (!source.is(DamageTypes.FALL) || !(entity instanceof Player player)) {
            return;
        }
        if (amount > 10F || player.getRandom().nextDouble() < 0.2D) {
            player.addEffect(new MobEffectInstance(ModMobEffects.BONE_FRACTURE_LEG
                    .getHolder(), MobEffectInstance.INFINITE_DURATION, 0, false, false, true));
            LOG.debug("LivingEntity {} has broken leg from fall damage", player.getName().getString());
        }
    }

    public static void onPlayerTakesDamage(DamageSource source, LivingEntity entity) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        DAMAGE_TYPES_FOR_BONE_FRACTURE.keySet()
                .stream()
                .filter(source::is)
                .findFirst()
                .ifPresent(type -> atomicBoolean
                        .set(applyEffect(entity, DAMAGE_TYPES_FOR_BONE_FRACTURE.get(type), source)));
        if (atomicBoolean.get()) {
            return;
        }

        final Entity damageSourceEntity = source.getEntity();
        if (damageSourceEntity == null || !damageSourceEntity.isAlive()) {
            return;
        }
        Optional.ofNullable(ENTITY_TYPES_FOR_BONE_FRACTURE.get(damageSourceEntity.getType()))
                .ifPresent(bleedingChance -> atomicBoolean.set(applyEffect(entity, bleedingChance, source)));
    }

    private static boolean applyEffect(LivingEntity entity, Double bleedingChance, DamageSource source) {
        if (entity.level().getRandom().nextFloat() < bleedingChance) {
            if (entity.level().getRandom().nextFloat() < bleedingChance / 2) {
                entity.addEffect(
                        new MobEffectInstance(ModMobEffects.BONE_FRACTURE_ARM_MAIN.getHolder(),
                                MobEffectInstance.INFINITE_DURATION),
                        source.getEntity());
            } else {
                entity.addEffect(
                        new MobEffectInstance(ModMobEffects.BONE_FRACTURE_ARM.getHolder(),
                                MobEffectInstance.INFINITE_DURATION),
                        source.getEntity());
            }
            return true;
        }
        return false;
    }

    public static boolean onPlayerUse(InteractionHand hand, LivingEntity entity) {
        if (hand.equals(InteractionHand.OFF_HAND) && entity.hasEffect(ModMobEffects.BONE_FRACTURE_ARM.getHolder())) {
            LOG.debug("Player {} has broken arm, canceling right-click action",
                    entity.getName().getString());
            return true;
        } else {
            return false;
        }
    }

    public static InteractionResult onPlayerUseWithResult(InteractionHand hand, LivingEntity entity) {
        return onPlayerUse(hand, entity) ? InteractionResult.PASS : InteractionResult.FAIL;
    }

}
