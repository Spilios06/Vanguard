package me.rex.vanguard.module.modules.movement;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import net.minecraft.entity.attribute.EntityAttributes;
import org.lwjgl.glfw.GLFW;

public class Step extends Module {
    public float stepHeight = mc.player.getStepHeight();
    public Step(){
        super("Step", Category.MOVEMENT, -1, false);
    }
    @Override
    public void onDisable(){
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(stepHeight);
    }

    @Override
    public void onEnable() {
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(2f);
    }
}
