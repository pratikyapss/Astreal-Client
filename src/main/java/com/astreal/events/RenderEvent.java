package com.astreal.events;

import net.minecraft.client.gui.DrawContext;

/**
 * Fired when rendering HUD/GUI
 */
public class RenderEvent extends Event {
    private final DrawContext drawContext;
    private final float partialTicks;
    
    public RenderEvent(DrawContext context, float partialTicks) {
        this.drawContext = context;
        this.partialTicks = partialTicks;
    }
    
    public DrawContext getDrawContext() {
        return drawContext;
    }
    
    public float getPartialTicks() {
        return partialTicks;
    }
}
