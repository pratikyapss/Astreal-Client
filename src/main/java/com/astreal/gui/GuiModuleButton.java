package com.astreal.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.MinecraftClient;
import java.util.List;

import com.astreal.modules.Module;
import com.astreal.modules.Setting;

/**
 * Button for toggling modules in ClickGUI
 */
public class GuiModuleButton {
    private Module module;
    private double x, y, width, height;
    private boolean showSettings = false;
    
    public GuiModuleButton(Module module, double x, double y, double width, double height) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void render(DrawContext context, int mouseX, int mouseY) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        // Background color based on enabled state
        int bgColor = module.isEnabled() ? 0xFF16C784 : 0xFF2A2A3E;
        
        // Render button background
        context.fill((int)x, (int)y, (int)(x + width), (int)(y + height), bgColor);
        
        // Render border if hovered
        if (isMouseOver(mouseX, mouseY)) {
            context.fill((int)x, (int)y, (int)(x + width), (int)y + 1, 0xFFFFFF);
            context.fill((int)x, (int)(y + height - 1), (int)(x + width), (int)(y + height), 0xFFFFFF);
        }
        
        // Render module name
        String text = module.getName();
        if (module.isEnabled()) {
            text = "§l" + text;
        }
        
        context.drawText(client.textRenderer, text, (int)(x + 5), (int)(y + 6), 0xFFFFFF, false);
        
        // Render settings if expanded
        if (showSettings) {
            renderSettings(context, mouseX, mouseY);
        }
    }
    
    private void renderSettings(DrawContext context, int mouseX, int mouseY) {
        MinecraftClient client = MinecraftClient.getInstance();
        List<Setting<?>> settings = module.getSettings();
        
        double settingY = y + 20;
        for (Setting<?> setting : settings) {
            // Render setting name
            context.drawText(client.textRenderer, "  " + setting.getName() + ":", 
                           (int)(x + 10), (int)(settingY), 0xCCCCCC, false);
            
            settingY += 12;
        }
    }
    
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void offsetPosition(double deltaX, double deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }
    
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width &&
               mouseY >= y && mouseY <= y + height;
    }
    
    public void onClick(int button) {
        if (button == 0) { // Left click - toggle module
            module.toggle();
        } else if (button == 1) { // Right click - show settings
            showSettings = !showSettings;
        }
    }
    
    public void onKeyPress(int key) {
        // Handle keybind setting
    }
}
