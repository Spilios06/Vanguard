package me.rex.vanguard.module.modules.combat;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;

public class KillAura extends Module {
    public BindSetting bind = new BindSetting(-1, "Keybind");

    public KillAura() {
        super("Kill Aura", Category.COMBAT, -1);
        settings.add(bind);
    }
}
