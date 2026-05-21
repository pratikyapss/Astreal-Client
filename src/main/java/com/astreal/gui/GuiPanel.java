package com.astreal.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.MinecraftClient;
import java.util.*;

import com.astreal.client.AstrealClient;
import com.astreal.modules.Module;

/**
 * Panel for displaying module category
 */
public class GuiPanel {
    private String title;
    private double x, y;
    private double width, height;
    private Module.Category category;
    private double scrollOffset = 0;
    private List<GuiModuleButton> buttons = new ArrayList<>();
    private boolean expanded = true;
    
    private static final int HEADER_HEIGHT = 25;
    private static final int MODULE_HEIGHT = 20;
    private static final int PADDING = 5;
    
    public GuiPanel(String title, double x, double y, double width, double height, Module.Category category) {
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.category = category;
        
        initializeButtons();
    }
    
    private void initializeButtons() {
        AstrealClient client = AstrealClient.getInstance();
        List<Module> modules = client.getModuleManager().getModulesByCategory(category);
        
        for (int i = 0; i < modules.size(); i++) {
            Module module = modules.get(i);
            GuiModuleButton button = new GuiModuleButton(
                module,
                x + PADDING,
                y + HEADER_HEIGHT + (i * MODULE_HEIGHT),
                width - (2 * PADDING),
                MODULE_HEIGHT
            );
            buttons.add(button);
        }
    }
    
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        // Render panel background with glassmorphism effect
        context.fillGradient((int)x, (int)y, (int)(x + width), (int)(y + height), 
                            0x88000000, 0x88000000);
        
        // Render header
        context.fillGradient((int)x, (int)y, (int)(x + width), (int)(y + HEADER_HEIGHT),
                            0xFF16C784, 0xFF0F9366);
        
        // Render title
        context.drawText(client.textRenderer, "§l" + title, (int)(x + PADDING), (int)(y + 7), 0xFFFFFF, false);
        
        if (!expanded) return;
        
        // Render module buttons
        double currentY = y + HEADER_HEIGHT;
        for (GuiModuleButton button : buttons) {
            button.setPosition(x + PADDING, currentY);
            button.render(context, mouseX, mouseY);
            currentY += MODULE_HEIGHT + 1;
        }
    }
    
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width &&
               mouseY >= y && mouseY <= y + height;
    }
    
    public boolean isMouseOverTitle(double mouseX, double mouseY) {
        return mouseX >= x && mouseX <= x + width &&
               mouseY >= y && mouseY <= y + HEADER_HEIGHT;
    }
    
    public void setPosition(double x, double y) {
        double deltaX = x - this.x;
        double deltaY = y - this.y;
        
        this.x = x;
        this.y = y;
        
        for (GuiModuleButton button : buttons) {
            button.offsetPosition(deltaX, deltaY);
        }
    }
    
    public boolean onMouseClick(double mouseX, double mouseY, int button) {
        if (isMouseOverTitle(mouseX, mouseY)) {
            expanded = !expanded;
            return true;
        }
        
        for (GuiModuleButton btn : buttons) {
            if (btn.isMouseOver(mouseX, mouseY)) {
                btn.onClick(button);
                return true;
            }
        }
        return false;
    }
    
    public void scroll(double amount) {
        scrollOffset += amount * 5;
    }
    
    public void onKeyPress(int key) {
        for (GuiModuleButton button : buttons) {
            button.onKeyPress(key);
        }
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
}
