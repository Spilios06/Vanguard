package me.rex.vanguard.gui;

import me.rex.vanguard.module.Category;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ClickGUIScreen extends Screen {
    public static final ClickGUIScreen INSTANCE = new ClickGUIScreen();
    private List<Frame> frames;
    private ClickGUIScreen(){
        super(Text.literal("Click GUI"));
        frames = new ArrayList<>();
        int offset = 15;
        for(Category category : Category.values()){
            frames.add(new Frame(category, offset, 30, 80, 25));
            offset += 100;
        }
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        for(Frame frame : frames){
            frame.render(context, mouseX, mouseY, delta);
        }
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for(Frame frame : frames){
            frame.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.frames.forEach(component -> component.onKeyPressed(keyCode));
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.frames.forEach(component -> component.mouseReleased(mouseX, mouseY, button));
        super.mouseReleased(mouseX, mouseY, button);
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.frames.forEach(component -> component.mouseDragged(mouseX, mouseY));
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
}