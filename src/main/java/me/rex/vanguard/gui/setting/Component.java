package me.rex.vanguard.gui.setting;

import me.rex.vanguard.VanguardClient;
import me.rex.vanguard.gui.ModuleButton;
import me.rex.vanguard.settings.Setting;
import net.minecraft.client.gui.DrawContext;

import java.awt.*;

public class Component {

    public Setting setting;
    public ModuleButton parent;
    public int offset;
    protected boolean dragging = false;

    public Component(Setting setting, ModuleButton parent, int offset){
        this.setting = setting;
        this.parent = parent;
        this.offset = offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta){
        context.fill(parent.parent.x, parent.parent.y + offset, parent.parent.x + parent.parent.width, parent.parent.y + offset + parent.parent.height, Color.GRAY.getRGB());
        if(isHovered(mouseX, mouseY)){
            context.fill(parent.parent.x, parent.parent.y + offset, parent.parent.x + parent.parent.width, parent.parent.y + offset + parent.parent.height, new Color(0, 0, 0, 160).getRGB());
        }
    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX >= parent.parent.x && mouseX <= parent.parent.x + parent.parent.width && mouseY >= parent.parent.y + offset && mouseY <= parent.parent.y + offset + parent.parent.height;
    }

    public void onKeyPressed(int key) {

    }

    public void mouseDragged(double mouseX, double mouseY){}

    public void startDragging(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0) { // Only start dragging on left mouse button
            dragging = true;  // Mark as dragging
            mouseClicked(mouseX, mouseY, button);  // Optionally override for custom click behavior
        }
    }
}