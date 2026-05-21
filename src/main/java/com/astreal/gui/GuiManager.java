package com.astreal.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import java.util.*;

import com.astreal.client.AstrealClient;
import com.astreal.modules.Module;

/**
 * Manages the ClickGUI and HUD rendering
 * Implements modern glassmorphism design
 */
public class GuiManager {
    private ClickGuiScreen clickGuiScreen;
    private boolean showGui = false;
    private List<GuiPanel> panels = new ArrayList<>();
    
    public GuiManager() {
        initializeGui();
    }
    
    private void initializeGui() {
        // Create panels for each category
        Module.Category[] categories = Module.Category.values();
        for (int i = 0; i < categories.length; i++) {
            GuiPanel panel = new GuiPanel(
                categories[i].displayName,
                10 + i * 260,
                10,
                250,
                500,
                categories[i]
            );
            panels.add(panel);
        }
    }
    
    public void toggleGui() {
        showGui = !showGui;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (showGui && clickGuiScreen == null) {
            clickGuiScreen = new ClickGuiScreen(Text.of("Astreal GUI"), panels);
            client.setScreen(clickGuiScreen);
        } else if (!showGui) {
            if (client.currentScreen instanceof ClickGuiScreen) {
                client.setScreen(null);
            }
            showGui = false;
        }
    }
    
    public boolean isGuiOpen() {
        return showGui;
    }
    
    public void renderHud(DrawContext context) {
        if (showGui) return;
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        
        // Render HUD elements
        renderModuleList(context, client);
        renderCoordinates(context, client);
        renderServerInfo(context, client);
    }
    
    private void renderModuleList(DrawContext context, MinecraftClient client) {
        AstrealClient astreal = AstrealClient.getInstance();
        List<Module> enabledModules = astreal.getModuleManager().getEnabledModules();
        
        int x = context.getScaledWindowWidth() - 150;
        int y = 10;
        int spacing = 12;
        
        for (Module module : enabledModules) {
            String text = "§f" + module.getName();
            context.drawText(client.textRenderer, text, x, y, 0xFFFFFF, false);
            y += spacing;
        }
    }
    
    private void renderCoordinates(DrawContext context, MinecraftClient client) {
        double x = client.player.getX();
        double y = client.player.getY();
        double z = client.player.getZ();
        
        String coords = String.format("XYZ: %.1f / %.1f / %.1f", x, y, z);
        context.drawText(client.textRenderer, coords, 10, context.getScaledWindowHeight() - 20, 0xFFFFFF, false);
    }
    
    private void renderServerInfo(DrawContext context, MinecraftClient client) {
        String fps = "FPS: " + client.getCurrentFps();
        context.drawText(client.textRenderer, fps, 10, context.getScaledWindowHeight() - 10, 0xFFFFFF, false);
    }
    
    public void onKeyPress(int key) {
        if (clickGuiScreen != null) {
            clickGuiScreen.onKeyPress(key);
        }
    }
}
