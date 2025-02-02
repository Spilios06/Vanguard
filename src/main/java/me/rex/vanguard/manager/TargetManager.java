package me.rex.vanguard.manager;

import me.rex.vanguard.VanguardClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class TargetManager{
    public boolean isReachable(int range) {
        return false;
    }

    public PlayerEntity getNearestTarget() {
        return null;
    }

    public PlayerEntity getLowestHealthTarget() {
        return null;
    }

    public void focusOnTarget(PlayerEntity target) {
    }
}