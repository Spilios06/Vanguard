package me.rex.vanguard.module.modules.misc;

import me.rex.vanguard.event.events.KeyPressEvent;
import me.rex.vanguard.manager.FriendManager;
import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import org.lwjgl.glfw.GLFW;

public class MCF extends Module {
    public MCF() {
        super("Middle Click Friend", Category.MISC, -1);
    }

    @Override
    public void onKeyPress(KeyPressEvent event) {
        if(event.key == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
            FriendManager.INSTANCE.friends.add(""); //TODO: get the name of the player the player is looking and add check if the crosshair is on a player entity within a certain range
        }
    }
}