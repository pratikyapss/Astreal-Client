package com.astreal.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.astreal.managers.ModuleManager;
import com.astreal.managers.ConfigManager;
import com.astreal.managers.EventManager;
import com.astreal.managers.CommandManager;
import com.astreal.gui.GuiManager;
import com.astreal.utils.Logger as AstrealLogger;

/**
 * Main entry point for Astreal Client
 * Initializes all core systems and managers
 */
public class AstrealClient implements ClientModInitializer {
    public static final String MOD_ID = "astrealclient";
    public static final String MOD_NAME = "Astreal Client";
    public static final String VERSION = "1.0.0";
    
    public static final Logger LOGGER = LoggerFactory.getLogger("Astreal");
    
    private static AstrealClient instance;
    private ModuleManager moduleManager;
    private ConfigManager configManager;
    private EventManager eventManager;
    private GuiManager guiManager;
    private CommandManager commandManager;
    
    private KeyBinding clickGuiKeyBinding;
    
    @Override
    public void onInitializeClient() {
        instance = this;
        LOGGER.info("Initializing Astreal Client v{} for Minecraft 1.21.1", VERSION);
        
        try {
            // Initialize managers
            this.eventManager = new EventManager();
            this.configManager = new ConfigManager();
            this.moduleManager = new ModuleManager();
            this.guiManager = new GuiManager();
            this.commandManager = new CommandManager();
            
            // Register keybindings
            registerKeybindings();
            
            // Register event listeners
            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                if (client.player != null) {
                    eventManager.onClientTick();
                    commandManager.handleInput();
                }
            });
            
            // Load configuration
            configManager.loadConfig();
            
            LOGGER.info("Astreal Client initialized successfully!");
            LOGGER.info("Press Right Shift to open ClickGUI");
            
        } catch (Exception e) {
            LOGGER.error("Failed to initialize Astreal Client", e);
        }
    }
    
    private void registerKeybindings() {
        clickGuiKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.astreal.clickgui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.astreal"
        ));
    }
    
    public static AstrealClient getInstance() {
        return instance;
    }
    
    public ModuleManager getModuleManager() {
        return moduleManager;
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public EventManager getEventManager() {
        return eventManager;
    }
    
    public GuiManager getGuiManager() {
        return guiManager;
    }
    
    public CommandManager getCommandManager() {
        return commandManager;
    }
    
    public KeyBinding getClickGuiKeyBinding() {
        return clickGuiKeyBinding;
    }
}
