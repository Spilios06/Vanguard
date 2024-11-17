package me.rex.vanguard.module.modules.misc;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import net.minecraft.item.Items;
import org.lwjgl.glfw.GLFW;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", Category.MISC, -1, false);
    }
    @Override
    public void onTick() {
        if (mc.player.isHolding(Items.EXPERIENCE_BOTTLE)) {
            mc.itemUseCooldown = 0;
        }
    }
}