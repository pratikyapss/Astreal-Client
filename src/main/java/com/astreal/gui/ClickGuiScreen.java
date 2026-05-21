package com.astreal.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import java.util.List;

/**
 * ClickGUI Screen with modern glassmorphism design
 */
public class ClickGuiScreen extends Screen {
    private List<GuiPanel> panels;
    private GuiPanel draggingPanel = null;
    private double dragOffsetX = 0;
    private double dragOffsetY = 0;
    
    public ClickGuiScreen(Text title, List<GuiPanel> panels) {
        super(title);
        this.panels = panels;
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Render semi-transparent background
        context.fillGradient(0, 0, this.width, this.height, 0xAA000000, 0xAA1A1A2E);
        
        // Render all panels
        for (GuiPanel panel : panels) {
            panel.render(context, mouseX, mouseY, delta);
        }
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        for (GuiPanel panel : panels) {
            if (panel.isMouseOver(mouseX, mouseY)) {
                panel.scroll(verticalAmount);
                return true;
            }
        }
        return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left click
            for (GuiPanel panel : panels) {
                if (panel.isMouseOverTitle(mouseX, mouseY)) {
                    draggingPanel = panel;
                    dragOffsetX = mouseX - panel.getX();
                    dragOffsetY = mouseY - panel.getY();
                    return true;
                }
                if (panel.onMouseClick(mouseX, mouseY, button)) {
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        draggingPanel = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (draggingPanel != null && button == 0) {
            draggingPanel.setPosition(mouseX - dragOffsetX, mouseY - dragOffsetY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }
    
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC key
            this.close();
            return true;
        }
        onKeyPress(keyCode);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
    
    public void onKeyPress(int key) {
        // Handle keybind changes
        for (GuiPanel panel : panels) {
            panel.onKeyPress(key);
        }
    }
    
    @Override
    public void close() {
        super.close();
    }
}
