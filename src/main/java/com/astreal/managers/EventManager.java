package com.astreal.managers;

import java.util.*;
import com.astreal.events.Event;

/**
 * Central event bus for all events
 * Manages event listener registration and firing
 */
public class EventManager {
    private final Map<Class<?>, List<EventListener>> listeners = new HashMap<>();
    
    /**
     * Register a listener for an event type
     */
    public <T extends Event> void register(Class<T> eventClass, EventListener<T> listener) {
        listeners.computeIfAbsent(eventClass, k -> Collections.synchronizedList(new ArrayList<>()))
                .add(listener);
    }
    
    /**
     * Unregister a listener
     */
    public <T extends Event> void unregister(Class<T> eventClass, EventListener<T> listener) {
        List<EventListener> eventListeners = listeners.get(eventClass);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }
    
    /**
     * Fire an event to all registered listeners
     */
    public void post(Event event) {
        List<EventListener> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (EventListener listener : new ArrayList<>(eventListeners)) {
                try {
                    listener.onEvent(event);
                    if (event.isCancelled()) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * Listener interface for events
     */
    @FunctionalInterface
    public interface EventListener<T extends Event> {
        void onEvent(T event);
    }
    
    /**
     * Fire client tick event
     */
    public void onClientTick() {
        // This will be called from the main client
        // Modules can listen for this event
    }
}
