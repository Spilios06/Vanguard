package me.rex.vanguard.module.modules.render;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.NumberSetting;

public class ViewmodelChanger extends Module {
    public NumberSetting scale = new NumberSetting("Scale", 0.1f, 2f, 1f);
    public BindSetting bind = new BindSetting(-1, "Bind");

    public ViewmodelChanger() {
        super("Viewmodel Changer", Category.RENDER, -1);
        addSettings(scale, bind);
    }

    @Override
    public void onEnable() {
        
    }
}