package ru.ancndz.bandagez.event;

import static java.util.Map.entry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ru.ancndz.bandagez.effect.Effects;
import ru.ancndz.bandagez.mod.BandagezMod;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber(modid = BandagezMod.MODID)
public class ForgeEventHandler {

	private static final Map<ResourceKey<DamageType>, Double> DAMAGE_TYPES_FOR_BLEEDING = Map.of(DamageTypes.ARROW,
			0.5D, DamageTypes.CACTUS, 0.2D, DamageTypes.EXPLOSION, 0.2D, DamageTypes.FALLING_STALACTITE, 0.5D,
			DamageTypes.STALAGMITE, 0.5D, DamageTypes.SWEET_BERRY_BUSH, 0.1D, DamageTypes.THORNS, 0.3D,
			DamageTypes.TRIDENT, 0.6D);

	private static final Map<ResourceKey<DamageType>, Double> DAMAGE_TYPES_FOR_HARD_BLEEDING = Map.of(DamageTypes.ARROW,
			0.2D, DamageTypes.EXPLOSION, 0.1D, DamageTypes.FALLING_STALACTITE, 0.1D, DamageTypes.STALAGMITE, 0.1D,
			DamageTypes.TRIDENT, 0.4D);

    @SuppressWarnings("rawtypes")
	private static final Map<EntityType, Double> ENTITY_TYPES_FOR_BLEEDING = Map.ofEntries(
			entry(EntityType.ZOMBIE, 0.3D), entry(EntityType.ZOMBIE_VILLAGER, 0.3D), entry(EntityType.ZOGLIN, 0.3D),
			entry(EntityType.CAVE_SPIDER, 0.2D), entry(EntityType.SPIDER, 0.2D), entry(EntityType.ENDERMAN, 0.2D),
			entry(EntityType.PIGLIN, 0.2D), entry(EntityType.PIGLIN_BRUTE, 0.2D),
			entry(EntityType.ZOMBIFIED_PIGLIN, 0.2D), entry(EntityType.PILLAGER, 0.2D),
			entry(EntityType.POLAR_BEAR, 0.2D), entry(EntityType.WITHER_SKELETON, 0.2D)

	);

    @SuppressWarnings("rawtypes")
	private static final Map<EntityType, Double> ENTITY_TYPES_FOR_HARD_BLEEDING = Map.ofEntries(
			entry(EntityType.ZOMBIE, 0.1D), entry(EntityType.CAVE_SPIDER, 0.1D), entry(EntityType.SPIDER, 0.1D),
			entry(EntityType.PIGLIN, 0.1D), entry(EntityType.PIGLIN_BRUTE, 0.1D),
			entry(EntityType.ZOMBIFIED_PIGLIN, 0.1D), entry(EntityType.PILLAGER, 0.1D),
			entry(EntityType.POLAR_BEAR, 0.2D), entry(EntityType.WITHER_SKELETON, 0.2D));

    @SubscribeEvent
    static void onServerStarting(ServerStartingEvent event) {
        event.getServer().getGameRules().getRule(GameRules.RULE_NATURAL_REGENERATION).set(false, event.getServer());
    }

	@SubscribeEvent
	static void onPlayerTakesDamage(LivingDamageEvent event) {
		final DamageSource source = event.getSource();
		final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        DAMAGE_TYPES_FOR_BLEEDING.keySet()
            .stream()
            .filter(source::is)
            .findFirst()
            .ifPresent(type -> atomicBoolean.set(applyEffect(event.getEntity(),
                    DAMAGE_TYPES_FOR_BLEEDING.get(type), Effects.BLEEDING.get(), source)));
		if (atomicBoolean.get()) {
			return;
		}

        DAMAGE_TYPES_FOR_HARD_BLEEDING.keySet()
            .stream()
            .filter(source::is)
            .findFirst()
            .ifPresent(type -> atomicBoolean.set(applyEffect(event.getEntity(),
                    DAMAGE_TYPES_FOR_HARD_BLEEDING.get(type), Effects.HARD_BLEEDING.get(), source)));
		if (atomicBoolean.get()) {
			return;
		}

		final Entity damageSourceEntity = source.getEntity();
		if (damageSourceEntity == null || !damageSourceEntity.isAlive()) {
			return;
		}
        Optional.ofNullable(ENTITY_TYPES_FOR_BLEEDING.get(damageSourceEntity.getType()))
            .ifPresent(bleedingChance -> atomicBoolean
                .set(applyEffect(event.getEntity(), bleedingChance, Effects.BLEEDING.get(), source)));
		if (atomicBoolean.get()) {
			return;
		}

		Optional.ofNullable(ENTITY_TYPES_FOR_HARD_BLEEDING.get(damageSourceEntity.getType()))
				.ifPresent(bleedingChance -> atomicBoolean
						.set(applyEffect(event.getEntity(), bleedingChance, Effects.HARD_BLEEDING.get(), source)));
	}

	private static boolean applyEffect(LivingEntity entity, Double bleedingChance, MobEffect effect,
			DamageSource source) {
		if (entity.level().getRandom().nextFloat() < bleedingChance) {
			entity.addEffect(new MobEffectInstance(effect, MobEffectInstance.INFINITE_DURATION), source.getEntity());
			return true;
		}
		return false;
	}
}
