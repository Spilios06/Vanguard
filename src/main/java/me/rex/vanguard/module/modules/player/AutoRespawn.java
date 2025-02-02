package me.rex.vanguard.module.modules.player;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", Category.PLAYER, -1);
    }

    @Override
    public void onTick() {
        assert mc.player != null;
        if (mc.player.isDead()) {
            mc.player.requestRespawn();
        }
    }
}
