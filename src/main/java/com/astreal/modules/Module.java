package com.astreal.modules;

import java.util.*;
import com.astreal.utils.KeybindUtil;

/**
 * Base class for all modules (features/hacks)
 * Provides lifecycle management, settings, and keybinding
 */
public abstract class Module {
    protected String name;
    protected String description;
    protected Category category;
    protected boolean enabled = false;
    protected int keyCode = -1;
    
    protected List<Setting<?>> settings = new ArrayList<>();
    
    public enum Category {
        COMBAT("Combat"),
        MOVEMENT("Movement"),
        RENDER("Render"),
        PLAYER("Player"),
        UTILITY("Utility"),
        PERFORMANCE("Performance"),
        HUD("HUD"),
        MISC("Misc");
        
        public final String displayName;
        
        Category(String displayName) {
            this.displayName = displayName;
        }
    }
    
    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
    
    /**
     * Called when module is enabled
     */
    public abstract void onEnable();
    
    /**
     * Called when module is disabled
     */
    public abstract void onDisable();
    
    /**
     * Called every tick
     */
    public abstract void onUpdate();
    
    /**
     * Called when rendering HUD
     */
    public abstract void onRender();
    
    /**
     * Toggle the module on/off
     */
    public void toggle() {
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }
    
    /**
     * Enable the module
     */
    public void enable() {
        if (!enabled) {
            enabled = true;
            try {
                onEnable();
            } catch (Exception e) {
                e.printStackTrace();
                enabled = false;
            }
        }
    }
    
    /**
     * Disable the module
     */
    public void disable() {
        if (enabled) {
            enabled = false;
            try {
                onDisable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }
    
    public int getKeyCode() {
        return keyCode;
    }
    
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
    
    public List<Setting<?>> getSettings() {
        return settings;
    }
    
    public <T> Setting<T> addSetting(Setting<T> setting) {
        settings.add(setting);
        return setting;
    }
}
