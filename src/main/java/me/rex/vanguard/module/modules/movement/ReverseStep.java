package me.rex.vanguard.module.modules.movement;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;

public class ReverseStep extends Module {
    public ReverseStep(){
        super("ReverseStep", Category.MOVEMENT, -1);
    }
    @Override
    public void onTick(){
        if (mc.player.isInLava() || mc.player.isTouchingWater() || !mc.player.isOnGround()) return;
        mc.player.addVelocity(0, -1, 0);
    }
}
