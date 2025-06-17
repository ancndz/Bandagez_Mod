package ru.ancndz.bandagez.effect;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import ru.ancndz.bandagez.mod.BandagezMod;

public class Bleeding extends MobEffect {

	private final boolean hard;
	@Nullable
	private String descriptionId;

	protected Bleeding(boolean hard, MobEffectCategory category, int color) {
		super(category, color);
		this.hard = hard;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int food) {
		entity.hurt(entity.damageSources().magic(), 2.0F);
	}

	@Override
	public boolean isDurationEffectTick(int tick, int amplifier) {
		int j = 25 >> amplifier;
		if (j > 0) {
			return tick % j == 0;
		} else {
			return true;
		}
	}

	@Override
	protected @NotNull String getOrCreateDescriptionId() {
		if (this.descriptionId == null) {
			this.descriptionId = Util.makeDescriptionId("effect",
					ResourceLocation.fromNamespaceAndPath(BandagezMod.MODID, hard ? "hard_bleed" : "bleed"));
		}
		return this.descriptionId;
	}
}
