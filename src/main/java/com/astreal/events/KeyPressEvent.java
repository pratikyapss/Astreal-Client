package com.astreal.events;

/**
 * Fired when a key is pressed
 */
public class KeyPressEvent extends Event {
    private final int key;
    private final int scanCode;
    private final int action;
    private final int mods;
    
    public KeyPressEvent(int key, int scanCode, int action, int mods) {
        this.key = key;
        this.scanCode = scanCode;
        this.action = action;
        this.mods = mods;
    }
    
    public int getKey() {
        return key;
    }
    
    public int getScanCode() {
        return scanCode;
    }
    
    public int getAction() {
        return action;
    }
    
    public int getMods() {
        return mods;
    }
}
