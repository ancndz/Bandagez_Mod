package ru.ancndz.bandagez.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.ancndz.bandagez.effect.ModEffects;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber(modid = BandagezMod.MODID)
public class BleedingEventHandler {

    @SuppressWarnings("rawtypes")
    private static final Map<DamageSource, Double> DAMAGE_SOURCES_FOR_BLEEDING = new HashMap<>();
    static {
        DAMAGE_SOURCES_FOR_BLEEDING.put(DamageSource.CACTUS, 0.1D);
        DAMAGE_SOURCES_FOR_BLEEDING.put(DamageSource.SWEET_BERRY_BUSH, 0.1D);
    }

    @SuppressWarnings("rawtypes")
    private static final Map<EntityType, Double> ENTITY_TYPES_FOR_BLEEDING = new HashMap<>();
    static {
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.SKELETON, 0.4D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.ZOMBIE, 0.3D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.ZOMBIE_VILLAGER, 0.3D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.ZOGLIN, 0.3D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.CAVE_SPIDER, 0.2D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.SPIDER, 0.2D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.ENDERMAN, 0.2D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.PIGLIN, 0.2D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.PIGLIN_BRUTE, 0.2D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.ZOMBIFIED_PIGLIN, 0.2D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.PILLAGER, 0.2D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.POLAR_BEAR, 0.2D);
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.WITHER_SKELETON, 0.2D);
    }

    @SuppressWarnings("rawtypes")
    private static final Map<EntityType, Double> ENTITY_TYPES_FOR_HARD_BLEEDING = new HashMap<>();
    static {
        ENTITY_TYPES_FOR_BLEEDING.put(EntityType.SKELETON, 0.1D);
        ENTITY_TYPES_FOR_HARD_BLEEDING.put(EntityType.ZOMBIE, 0.1D);
        ENTITY_TYPES_FOR_HARD_BLEEDING.put(EntityType.CAVE_SPIDER, 0.1D);
        ENTITY_TYPES_FOR_HARD_BLEEDING.put(EntityType.SPIDER, 0.1D);
        ENTITY_TYPES_FOR_HARD_BLEEDING.put(EntityType.PIGLIN, 0.1D);
        ENTITY_TYPES_FOR_HARD_BLEEDING.put(EntityType.PIGLIN_BRUTE, 0.1D);
        ENTITY_TYPES_FOR_HARD_BLEEDING.put(EntityType.ZOMBIFIED_PIGLIN, 0.1D);
        ENTITY_TYPES_FOR_HARD_BLEEDING.put(EntityType.PILLAGER, 0.1D);
        ENTITY_TYPES_FOR_HARD_BLEEDING.put(EntityType.POLAR_BEAR, 0.2D);
        ENTITY_TYPES_FOR_HARD_BLEEDING.put(EntityType.WITHER_SKELETON, 0.2D);
    }

    @SubscribeEvent
    static void onPlayerTakesDamage(LivingDamageEvent event) {
        final DamageSource source = event.getSource();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        DAMAGE_SOURCES_FOR_BLEEDING.keySet()
                .stream()
                .filter(source::equals)
                .findFirst()
                .ifPresent(type -> atomicBoolean.set(applyEffect((LivingEntity) entity,
                        DAMAGE_SOURCES_FOR_BLEEDING.get(type),
                        ModEffects.BLEEDING.get())));
        if (atomicBoolean.get()) {
            return;
        }

        final Entity damageSourceEntity = source.getEntity();
        if (damageSourceEntity == null || !damageSourceEntity.isAlive()) {
            return;
        }
        Optional.ofNullable(ENTITY_TYPES_FOR_BLEEDING.get(damageSourceEntity.getType()))
                .ifPresent(bleedingChance -> atomicBoolean
                        .set(applyEffect((LivingEntity) entity, bleedingChance, ModEffects.BLEEDING.get())));
        if (atomicBoolean.get()) {
            return;
        }

        Optional.ofNullable(ENTITY_TYPES_FOR_HARD_BLEEDING.get(damageSourceEntity.getType()))
                .ifPresent(bleedingChance -> atomicBoolean
                        .set(applyEffect((LivingEntity) entity, bleedingChance, ModEffects.HARD_BLEEDING.get())));
    }

    private static boolean
            applyEffect(LivingEntity entity, Double bleedingChance, Effect effect) {
        if (entity.level.getRandom().nextFloat() < bleedingChance) {
            entity.addEffect(new EffectInstance(effect, -1));
            return true;
        }
        return false;
    }
}
