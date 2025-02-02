package me.rex.vanguard.gui.setting;
import me.rex.vanguard.settings.NumberSetting;
import me.rex.vanguard.settings.Setting;
import me.rex.vanguard.gui.ModuleButton;
import me.rex.vanguard.VanguardClient;
import net.minecraft.client.gui.DrawContext;
import java.awt.*;

public class NumberBox extends Component {
    private NumberSetting NumberSetting;
    private boolean dragging = false;
    public NumberBox(Setting setting, ModuleButton parent, int offset) {
        super(setting, parent, offset);
        this.NumberSetting = (NumberSetting) setting;
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        // Render slider bar
        int sliderX = parent.parent.x + 2;
        int sliderWidth = parent.parent.width - 10;
        int sliderY = parent.parent.y + offset + 15;
        int sliderHeight = 2;
        // Draw slider background (gray bar)
        context.fill(sliderX, sliderY, sliderX + sliderWidth, sliderY + sliderHeight, new Color(50, 50, 50).getRGB());
        // Draw slider handle
        int handleWidth = 3;
        int handleHeight = 6;
        int handleX = sliderX + (int) ((NumberSetting.value - NumberSetting.min) / (NumberSetting.max - NumberSetting.min) * sliderWidth) - (handleWidth / 2);
        int handleY = sliderY - handleHeight / 2; // Vertically center the handle
        context.fill(handleX, handleY, handleX + handleWidth, handleY + handleHeight, Color.GREEN.getRGB()); // 6px padding for visual appeal
        // Display the current value of the slider
        context.drawTextWithShadow(VanguardClient.INSTANCE.mc.textRenderer, String.format("%.2f", NumberSetting.value),
                                   sliderX + sliderWidth + 5, sliderY, Color.GREEN.getRGB());
        // Display the setting name
        context.drawTextWithShadow(VanguardClient.INSTANCE.mc.textRenderer, setting.name,
                                   parent.parent.x + 2, parent.parent.y + offset + 2, Color.WHITE.getRGB());
    }
    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        if (isHovered(mouseX, mouseY)) {
            dragging = true; // Start dragging if mouse is over slider bar
            updateValueFromMouse(mouseX); // Update the value based on where the click happened
        }
    }
    @Override
    public void mouseReleased(double mouseX, double mouseY, int button) {
        super.mouseReleased(mouseX, mouseY, button);
        dragging = false; // Stop dragging
    }
    @Override
    public void mouseDragged(double mouseX, double mouseY) {
        if (dragging) {
            // Update slider value based on mouse position
            int sliderX = parent.parent.x + 2;
            int sliderWidth = parent.parent.width - 4;
            double newValue = NumberSetting.min + (mouseX - sliderX) / sliderWidth * (NumberSetting.max - NumberSetting.min);
            NumberSetting.setValueDouble(Math.min(NumberSetting.max, Math.max(NumberSetting.min, newValue))); // Clamp to min/max
        }
    }
    // Helper method to calculate the slider value based on mouse position
    private void updateValueFromMouse(double mouseX) {
        int sliderX = parent.parent.x + 2;
        int sliderWidth = parent.parent.width - 4;
        // Ensure the mouseX value stays within the slider bounds
        double newValue = NumberSetting.min + (mouseX - sliderX) / sliderWidth * (NumberSetting.max - NumberSetting.min);
        NumberSetting.setValueDouble(Math.min(NumberSetting.max, Math.max(NumberSetting.min, newValue))); // Clamp value within min and max
    }
}