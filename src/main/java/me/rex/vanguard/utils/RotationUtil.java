package me.rex.vanguard.utils;

import me.rex.vanguard.VanguardClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {
    public static float[] getRotations(PlayerEntity target) {
        return getRotations(target.getPos().add(0, target.getEyeHeight(target.getPose()), 0));
    }

    public static float[] getRotations(Vec3d target) {
        double deltaX = target.x - VanguardClient.INSTANCE.mc.player.getX();
        double deltaZ = target.z - VanguardClient.INSTANCE.mc.player.getZ();
        double deltaY = target.y - VanguardClient.INSTANCE.mc.player.getEyeY();
        double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float yaw = (float) Math.toDegrees(Math.atan2(-deltaX, deltaZ));
        float pitch = (float) -Math.toDegrees(Math.atan2(deltaY, distanceXZ));
        return new float[]{yaw, pitch};
    }
}