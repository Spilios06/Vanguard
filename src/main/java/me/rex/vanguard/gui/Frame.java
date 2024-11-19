package me.rex.vanguard.gui;

import me.rex.vanguard.gui.setting.Component;
import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import me.rex.vanguard.VanguardClient;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame {
    public int x, y, width, height;
    public Category category;
    public boolean draggable;
    private List<ModuleButton> buttons;
    public Frame(Category category, int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.category = category;
        this.width = width;
        this.height = height;
        this.draggable = false;
        buttons = new ArrayList<ModuleButton>();
        int offset = height;
        for(Module module : ModuleManager.INSTANCE.getModulesInCategory(category)){
            buttons.add(new ModuleButton(module, offset,this));
            offset += height;
        }
    }
    public void render(DrawContext context, int mouseX, int mouseY, float delta){
        context.fill(this.x, this.y, this.x + this.width, this.y + this.height, Color.MAGENTA.getRGB());
        context.drawTextWithShadow(VanguardClient.INSTANCE.mc.textRenderer, category.name(), x + 2, y + 2, -1);
        for (ModuleButton button : buttons){
            button.render(context, mouseX, mouseY, delta);
        }
    }
    public void mouseClicked(double mouseX, double mouseY, int button) {
        for (ModuleButton moduleButton : buttons){
            moduleButton.mouseClicked(mouseX, mouseY, button);
            for(Component component : moduleButton.components){
                if(component.isHovered(mouseX, mouseY)){
                    component.mouseClicked(mouseX, mouseY, button);
                }
            }
        }
    }

    public void updateButtons() {
        int offset = height;
        for(ModuleButton button : buttons){
            button.offset = offset;
            offset += height;
            if(button.extended){
                for(Component component : button.components){
                    if (component.setting.visible){
                        component.offset = offset;
                        offset += component.parent.parent.height;
                        //component.render();
                    }
                }
            }
        }
    }

    public void onKeyPressed(int keyCode) {
        for(ModuleButton button : buttons){
            for(Component component : button.components){
                component.onKeyPressed(keyCode);
            }
        }
    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        for(ModuleButton button1 : buttons){
            for(Component component : button1.components){
                component.mouseReleased(mouseX, mouseY, button);
            }
        }
    }

    public void mouseDragged(double mouseX, double mouseY) {
        for(ModuleButton button : buttons){
            for(Component component : button.components){
                component.mouseDragged(mouseX, mouseY);
            }
        }
    }
}