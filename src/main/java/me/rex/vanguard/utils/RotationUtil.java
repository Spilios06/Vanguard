package me.rex.vanguard.utils;

import net.minecraft.entity.player.PlayerEntity;
import me.rex.vanguard.VanguardClient;

public class RotationUtil {
    public static float[] getRotations(PlayerEntity target) {
        double deltaX = target.getX() - VanguardClient.INSTANCE.mc.player.getX();
        double deltaZ = target.getZ() - VanguardClient.INSTANCE.mc.player.getZ();
        double deltaY = target.getEyeY() - VanguardClient.INSTANCE.mc.player.getEyeY();
        double distanceXZ = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
        float yaw = (float) Math.toDegrees(Math.atan2(-deltaX, deltaZ));
        float pitch = (float) -Math.toDegrees(Math.atan2(deltaY, distanceXZ));
        return new float[]{yaw, pitch};
    }
}