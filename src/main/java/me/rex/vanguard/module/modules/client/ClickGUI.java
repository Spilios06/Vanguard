package me.rex.vanguard.module.modules.client;

import com.google.common.eventbus.Subscribe;
import me.rex.vanguard.event.events.KeyPressEvent;
import me.rex.vanguard.gui.ClickGUIScreen;
import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import org.lwjgl.glfw.GLFW;

public class ClickGUI extends Module {
    public BindSetting bind = new BindSetting(-1, "Keybind");
    public ClickGUI(){
        super("ClickGUI", Category.CLIENT, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }
    @Override
    public void onEnable(){
        mc.setScreenAndRender(ClickGUIScreen.INSTANCE);
    }

    @Override
    public void onDisable() {
        mc.setScreen(null);
    }

    @Override
    public void onTick() {
        super.onTick();
    }

    @Subscribe
    public void onKeyPress(KeyPressEvent event){
        super.onKeyPress(event);
        if(event.key == GLFW.GLFW_KEY_ESCAPE && this.mc.currentScreen instanceof ClickGUIScreen){
            this.toggle();
        }
    }
}