package me.rex.vanguard.module.modules.player;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.utils.ChatUtil;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", Category.PLAYER, -1);
    }

    @Override
    public void onTick() {
        assert mc.player != null;
        if (mc.player.isDead()) {
            ChatUtil.sendServerMessage("You died at " + "X: " + mc.player.getX() + " " + "Y: " + mc.player.getY() + " " + "Z: " + mc.player.getZ());
            mc.player.requestRespawn();
        }
    }
}
