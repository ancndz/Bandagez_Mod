package ru.ancndz.bandagez.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import ru.ancndz.bandagez.config.ModConfiguration;
import ru.ancndz.bandagez.effect.ModMobEffects;
import ru.ancndz.bandagez.item.SupplyCustomTooltip;
import ru.ancndz.bandagez.item.Typed;
import ru.ancndz.bandagez.registration.RegistryObject;

import java.util.List;

public class WorldEventHandler {

    public static void onServerStarted(MinecraftServer server) {
        server.getGameRules().getRule(GameRules.RULE_NATURAL_REGENERATION).set(false, server);
    }

    public static void onItemTooltipEvent(ItemStack itemStack, List<Component> component) {
        final Object itemType = itemStack.getItem() instanceof Typed<?> typed ? typed.getType() : itemStack.getItem();
        if (itemType instanceof SupplyCustomTooltip supplyCustomTooltip) {
            supplyCustomTooltip.addCustomTooltip(component);
        }
    }

    public static void onPlayerLoggedIn(LivingEntity entity) {
        updateEffect(entity, ModMobEffects.BLEEDING);
        updateEffect(entity, ModMobEffects.HARD_BLEEDING);
    }

    private static void updateEffect(LivingEntity entity, RegistryObject<MobEffect> effect) {
        if (entity.hasEffect(effect.getHolder())) {
            final MobEffectInstance effectInstance = entity.getEffect(effect.getHolder());
            effectInstance.update(new MobEffectInstance(effect.getHolder(),
                effectInstance.getDuration(),
                effectInstance.getAmplifier(),
                effectInstance.isAmbient(),
                ModConfiguration.getClientConfig().getShowParticles(),
                true));
        }
    }

}
