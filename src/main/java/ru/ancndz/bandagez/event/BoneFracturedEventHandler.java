package ru.ancndz.bandagez.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.ancndz.bandagez.effect.ModEffects;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber(modid = BandagezMod.MODID)
public class BoneFracturedEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(BoneFracturedEventHandler.class);

    private static final Map<DamageSource, Double> DAMAGE_TYPES_FOR_BONE_FRACTURE = new HashMap<>();
    static {
        DAMAGE_TYPES_FOR_BONE_FRACTURE.put(DamageSource.FALLING_BLOCK, 0.2D);
    }

    private static final Map<EntityType, Double> ENTITY_TYPES_FOR_BONE_FRACTURE = new HashMap<>();
    static {
        ENTITY_TYPES_FOR_BONE_FRACTURE.put(EntityType.ZOMBIE, 0.1D);
        ENTITY_TYPES_FOR_BONE_FRACTURE.put(EntityType.ZOMBIFIED_PIGLIN, 0.3D);
        ENTITY_TYPES_FOR_BONE_FRACTURE.put(EntityType.POLAR_BEAR, 0.3D);
        ENTITY_TYPES_FOR_BONE_FRACTURE.put(EntityType.WITHER_SKELETON, 0.2D);
    }

    @SubscribeEvent
    static void onPlayerFall(LivingFallEvent event) {
        final LivingEntity entity = (LivingEntity) event.getEntity();
        if (entity.hasEffect(ModEffects.BONE_FRACTURE_LEG.get())) {
            event.setDamageMultiplier(event.getDamageMultiplier() + 0.5F);
        }
    }

    @SubscribeEvent
    static void onPlayerTakesFallDamage(LivingDamageEvent event) {
        final DamageSource source = event.getSource();
        final LivingEntity entity = (LivingEntity) event.getEntity();
        if (!source.equals(DamageSource.FALL) || !(entity instanceof PlayerEntity)) {
            return;
        }
        if (event.getAmount() > 10F || entity.getRandom().nextDouble() < 0.2D) {
            entity.addEffect(new EffectInstance(ModEffects.BONE_FRACTURE_LEG.get(), -1, 0, false, false, true));
            LOG.debug("LivingEntity {} has broken leg from fall damage", entity.getName().getString());
        }
    }

    @SubscribeEvent
    static void onPlayerTakesDamage(LivingDamageEvent event) {
        final DamageSource source = event.getSource();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        DAMAGE_TYPES_FOR_BONE_FRACTURE.keySet()
                .stream()
                .filter(source::equals)
                .findFirst()
                .ifPresent(type -> atomicBoolean
                        .set(applyEffect((LivingEntity) event.getEntity(), DAMAGE_TYPES_FOR_BONE_FRACTURE.get(type))));
        if (atomicBoolean.get()) {
            return;
        }

        final Entity damageSourceEntity = source.getEntity();
        if (damageSourceEntity == null || !damageSourceEntity.isAlive()) {
            return;
        }
        Optional.ofNullable(ENTITY_TYPES_FOR_BONE_FRACTURE.get(damageSourceEntity.getType()))
                .ifPresent(bleedingChance -> atomicBoolean
                        .set(applyEffect((LivingEntity) event.getEntity(), bleedingChance)));
    }

    private static boolean applyEffect(LivingEntity entity, Double bleedingChance) {
        if (entity.level.getRandom().nextFloat() < bleedingChance) {
            if (entity.level.getRandom().nextFloat() < bleedingChance / 2) {
                entity.addEffect(new EffectInstance(ModEffects.BONE_FRACTURE_ARM_MAIN.get(), -1));
            } else {
                entity.addEffect(new EffectInstance(ModEffects.BONE_FRACTURE_ARM.get(), -1));
            }
            return true;
        }
        return false;
    }

    @SubscribeEvent
    static boolean onPlayerUse(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand().equals(Hand.OFF_HAND)
                && event.getEntity() instanceof PlayerEntity
                && ((PlayerEntity) event.getEntity()).hasEffect(ModEffects.BONE_FRACTURE_ARM.get())) {
            LOG.debug("Player {} has broken arm, canceling right-click action",
                    event.getEntity().getName().getString());
            return true;
        } else {
            return false;
        }
    }

}
