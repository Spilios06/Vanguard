package me.rex.vanguard.module.modules.movement;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.NumberSetting;
import net.minecraft.entity.attribute.EntityAttributes;
import org.lwjgl.glfw.GLFW;

public class Step extends Module {
    public NumberSetting height = new NumberSetting("Height", 1f, 5f, 2f);
    public float stepHeight = mc.player.getStepHeight();
    public Step(){
        super("Step", Category.MOVEMENT, -1, false);
    }
    @Override
    public void onDisable(){
        assert mc.player != null;
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(stepHeight);
    }

    @Override
    public void onEnable() {
        assert mc.player != null;
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(height.getValueFloat());
    }
}
