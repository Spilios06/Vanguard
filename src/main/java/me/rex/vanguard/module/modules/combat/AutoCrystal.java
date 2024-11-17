package me.rex.vanguard.module.modules.combat;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.BoolSetting;
import me.rex.vanguard.settings.EnumSetting;
import me.rex.vanguard.settings.NumberSetting;
import org.lwjgl.glfw.GLFW;
import me.rex.vanguard.module.modules.combat.AutoCrystal.renderMode;

public class AutoCrystal extends Module {
    public NumberSetting placeDistance = new NumberSetting("Place Distance", 1, 8, 6);
    public BoolSetting silentSwitch = new BoolSetting("Silent Switch", false);
    public static EnumSetting renderType = new EnumSetting("Render Mode", renderMode.BOTH, renderMode.FILL, renderMode.OUTLINE, renderMode.BOTH);
    public BindSetting bind = new BindSetting(-1, "Keybind");
    public AutoCrystal(){
        super("AutoCrystal", Category.COMBAT, -1, false);
        addSettings(placeDistance, silentSwitch, renderType, bind);
    }
    public enum renderMode{
        FILL,
        OUTLINE,
        BOTH
    }
}