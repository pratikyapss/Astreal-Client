package com.astreal.events;

/**
 * Fired every client tick
 */
public class ClientTickEvent extends Event {
    private final float partialTicks;
    
    public ClientTickEvent(float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return partialTicks;
    }
}

