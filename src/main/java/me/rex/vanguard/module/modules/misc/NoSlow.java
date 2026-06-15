package me.rex.vanguard.module.modules.misc;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;

public class NoSlow extends Module {
    public static NoSlow INSTANCE;

    public NoSlow() {
        super("NoSlow", Category.MISC, -1);
        INSTANCE = this;
    }
}
