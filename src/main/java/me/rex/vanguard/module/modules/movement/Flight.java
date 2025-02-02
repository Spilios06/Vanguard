package me.rex.vanguard.module.modules.movement;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.BoolSetting;
import me.rex.vanguard.settings.NumberSetting;

public class Flight extends Module {
    public NumberSetting speed = new NumberSetting("Speed", 0, 10, 2);
    public BoolSetting bypass = new BoolSetting("Anti Kick", false);
    public BindSetting bind = new BindSetting(-1, "Bind");
    public Flight() {
        super("Flight", Category.MOVEMENT, -1);
        addSettings(speed, bypass, bind);
    }
    @Override
    public void onTick(){
        assert mc.player != null;
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().setFlySpeed(speed.getValueFloat());
        super.onTick();
    }

    @Override
    public void onDisable(){
        assert mc.player != null;
        mc.player.getAbilities().flying = false;
        super.onDisable();
    }
}
