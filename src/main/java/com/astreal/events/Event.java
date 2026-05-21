package com.astreal.events;

/**
 * Base class for all events in Astreal Client
 */
public abstract class Event {
    private boolean cancelled = false;
    
    public void cancel() {
        this.cancelled = true;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
