package me.rex.vanguard.module.modules.combat;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.NumberSetting;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {
    public static AutoTotem INSTANCE;
    public BindSetting bind = new BindSetting(-1, "Keybind");
    public NumberSetting health = new NumberSetting("Health", 1, 20, 10, 1);

    public AutoTotem() {
        super("Auto Totem", Category.COMBAT, -1);
        INSTANCE = this;
        addSettings(health, bind);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (!(mc.player.currentScreenHandler instanceof PlayerScreenHandler)) return;

        float totalHealth = mc.player.getHealth() + mc.player.getAbsorptionAmount();
        boolean lowHealth = totalHealth <= health.getValueFloat();

        if (lowHealth) {
            if (mc.player.getOffHandStack().getItem() != Items.TOTEM_OF_UNDYING) {
                findAndSwap(Items.TOTEM_OF_UNDYING);
            }
        } else if (mc.player.getMainHandStack().getItem() instanceof SwordItem && mc.options.useKey.isPressed()) {
            if (mc.player.getOffHandStack().getItem() != Items.ENCHANTED_GOLDEN_APPLE) {
                findAndSwap(Items.ENCHANTED_GOLDEN_APPLE);
            }
        } else {
            if (mc.player.getOffHandStack().getItem() != Items.END_CRYSTAL) {
                findAndSwap(Items.END_CRYSTAL);
            }
        }
    }

    private void findAndSwap(Item item) {
        PlayerInventory inv = mc.player.getInventory();
        int syncId = mc.player.currentScreenHandler.syncId;

        for (int i = 0; i < 36; i++) {
            if (inv.main.get(i).getItem() == item) {
                int containerSlot = i < 9 ? 36 + i : i;
                mc.interactionManager.clickSlot(syncId, containerSlot, 40, SlotActionType.SWAP, mc.player);
                return;
            }
        }
    }
}
