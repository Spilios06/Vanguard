package me.rex.vanguard.module.modules.movement;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.NumberSetting;

public class Strafe extends Module {
    public static Strafe INSTANCE;
    public BindSetting bind = new BindSetting(-1, "Keybind");
    public NumberSetting speed = new NumberSetting("Speed", 0.1, 2, 0.45, 0.05);

    public Strafe() {
        super("Strafe", Category.MOVEMENT, -1);
        INSTANCE = this;
        addSettings(speed, bind);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (mc.player.isFallFlying() || mc.player.isTouchingWater() || mc.player.isSubmergedInWater()) return;

        boolean moving = mc.player.input.movementForward != 0 || mc.player.input.movementSideways != 0;
        if (!moving) return;

        if (mc.player.isOnGround()) {
            mc.player.jump();
        }
    }
}
