package me.rex.vanguard.gui.setting;

import me.rex.vanguard.VanguardClient;
import me.rex.vanguard.event.Event;
import me.rex.vanguard.gui.ModuleButton;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.Setting;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

public class BindBox extends Component{
    private BindSetting bindSetting;
    public boolean isListening;

    public BindBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.bindSetting = (BindSetting) setting;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (this.isListening) {
            context.drawTextWithShadow(VanguardClient.INSTANCE.mc.textRenderer, "Press a Key...", parent.parent.x + 2, parent.parent.y + offset + 2, -1);
        } else {
            String str = GLFW.glfwGetKeyName(this.bindSetting.getValue(), GLFW.glfwGetKeyScancode(this.bindSetting.getValue()));
            if (str == null) str = "KEY.KEYBOARD." + this.bindSetting.getValue();
            str = str.replace("KEY.KEYBOARD", "").replace(".", " ");
            context.drawTextWithShadow(VanguardClient.INSTANCE.mc.textRenderer, this.setting.name + " " + str, parent.parent.x + 2, parent.parent.y + offset + 2, -1);
        }
    }

    @Override
    public void onKeyPressed(int key) {
        if (!this.isListening) return;
        BindSetting bind = new BindSetting(key, "Keybind");
        if (key == GLFW.GLFW_KEY_DELETE
                || key == GLFW.GLFW_KEY_BACKSPACE
                || key == GLFW.GLFW_KEY_ESCAPE) {
            bind = new BindSetting(-1, "Keybind");
        }
        this.bindSetting.value = bind.value;
        this.isListening = false;
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) {
            this.isListening = !this.isListening;
        }
    }
}