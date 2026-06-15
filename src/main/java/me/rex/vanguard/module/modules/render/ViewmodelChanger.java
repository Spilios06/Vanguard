package me.rex.vanguard.module.modules.render;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.NumberSetting;

public class ViewmodelChanger extends Module {
    public static ViewmodelChanger INSTANCE;

    public NumberSetting scale = new NumberSetting("Scale", 0.1f, 2f, 1f, 0.1f);
    public NumberSetting posX = new NumberSetting("Pos X", -2.0, 2.0, 0.0, 0.1);
    public NumberSetting posY = new NumberSetting("Pos Y", -2.0, 2.0, 0.0, 0.1);
    public NumberSetting posZ = new NumberSetting("Pos Z", -2.0, 2.0, 0.0, 0.1);
    public BindSetting bind = new BindSetting(-1, "Bind");

    public ViewmodelChanger() {
        super("Viewmodel Changer", Category.RENDER, -1);
        INSTANCE = this;
        addSettings(scale, posX, posY, posZ, bind);
    }
}