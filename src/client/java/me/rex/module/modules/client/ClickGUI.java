package me.rex.module.modules.client;

import me.rex.module.Category;
import me.rex.module.Module;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    public ClickGUI(){
        super("ClickGUI", Category.CLIENT, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }
}