package com.astreal.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.render.entity.EntityRenderDispatcher;

/**
 * Mixin for optimizing entity rendering
 */
@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    
    @Inject(method = "render", at = @At("HEAD"))
    private void onEntityRender(CallbackInfo ci) {
        // Entity rendering optimization hooks
    }
}
