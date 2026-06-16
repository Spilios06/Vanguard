package me.rex.vanguard.mixin;

import me.rex.vanguard.module.modules.movement.Strafe;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class MixinStrafe {

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void onTickMovement(CallbackInfo ci) {
        if (Strafe.INSTANCE == null || !Strafe.INSTANCE.enabled) return;

        ClientPlayerEntity self = (ClientPlayerEntity) (Object) this;

        boolean moving = self.input.movementForward != 0 || self.input.movementSideways != 0;
        if (!moving) return;

        Vec3d vel = self.getVelocity();
        float yaw = self.getYaw();
        float forward = self.input.movementForward;
        float sideways = self.input.movementSideways;

        double mx = (double) (-Math.sin(Math.toRadians(yaw))) * (double) forward + (double) Math.cos(Math.toRadians(yaw)) * (double) sideways;
        double mz = (double) Math.cos(Math.toRadians(yaw)) * (double) forward + (double) Math.sin(Math.toRadians(yaw)) * (double) sideways;
        double len = Math.sqrt(mx * mx + mz * mz);
        if (len < 1.0E-7) return;

        mx /= len;
        mz /= len;

        double currentSpeed = Math.sqrt(vel.x * vel.x + vel.z * vel.z);
        double targetSpeed = Strafe.INSTANCE.speed.getValueFloat();
        if (currentSpeed < targetSpeed) {
            self.setVelocity(mx * targetSpeed, vel.y, mz * targetSpeed);
        }
    }
}
