package ru.ancndz.bandagez.effect.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import ru.ancndz.bandagez.config.ModConfiguration;

@OnlyIn(Dist.CLIENT)
public class BleedingParticle extends TextureSheetParticle {
    protected boolean isGlowing;

    BleedingParticle(ClientLevel clientLevel, double x, double y, double z) {
        super(clientLevel, x, y, z);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.lifetime = (int) (64.0 / (Math.random() * 0.8 + 0.2));
        setColor(1.0F, 0.2857143F, 0.083333336F);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float partialTick) {
        return this.isGlowing ? 240 : super.getLightColor(partialTick);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd = this.yd - this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= 0.98F;
                this.yd *= 0.98F;
                this.zd *= 0.98F;
            }
        }
    }

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void postMoveUpdate() {
        if (this.onGround) {
            this.remove();
            //this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
        }
    }

    public static BleedingParticle createParticle(
        SimpleParticleType particleType,
        ClientLevel clientLevel,
        double x,
        double y,
        double z,
        double xSpeed,
        double ySpeed,
        double zSpeed
    ) {
        final Boolean notHiddenOnClient = ModConfiguration.getClientConfig().getShowParticles();
        if (Boolean.TRUE.equals(notHiddenOnClient)) {
            return new BleedingParticle(clientLevel, x, y, z);
        } else {
            return null;
        }
    }
}
