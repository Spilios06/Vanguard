package me.rex.vanguard.manager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class TargetManager {
    public static final TargetManager INSTANCE = new TargetManager();
    private PlayerEntity currentTarget;

    public PlayerEntity getNearestTarget() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null || mc.player == null) return null;

        PlayerEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player) continue;
            if (FriendManager.INSTANCE.friends.contains(player.getName().getString())) continue;

            double distance = mc.player.distanceTo(player);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearest = player;
            }
        }

        return nearest;
    }

    public PlayerEntity getLowestHealthTarget() {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.world == null || mc.player == null) return null;

        PlayerEntity lowest = null;
        float lowestHealth = Float.MAX_VALUE;

        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player) continue;
            if (FriendManager.INSTANCE.friends.contains(player.getName().getString())) continue;

            float health = player.getHealth();
            if (health < lowestHealth) {
                lowestHealth = health;
                lowest = player;
            }
        }

        return lowest;
    }

    public boolean isReachable(int range) {
        PlayerEntity target = getNearestTarget();
        if (target == null) return false;
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return false;
        return mc.player.distanceTo(target) <= range;
    }

    public void focusOnTarget(PlayerEntity target) {
        this.currentTarget = target;
    }
}