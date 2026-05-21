package com.astreal.managers;

import java.util.*;
import com.astreal.modules.Module;
import com.astreal.modules.combat.*;
import com.astreal.modules.movement.*;
import com.astreal.modules.render.*;
import com.astreal.modules.utility.*;
import com.astreal.modules.performance.*;

/**
 * Manages all modules (hacks/features)
 * Handles registration, toggling, searching
 */
public class ModuleManager {
    private List<Module> modules = new ArrayList<>();
    
    public ModuleManager() {
        registerAllModules();
    }
    
    private void registerAllModules() {
        // Combat modules
        modules.add(new AimAssist());
        modules.add(new AutoSprint());
        modules.add(new CpsCounter());
        
        // Movement modules
        modules.add(new ToggleSprint());
        modules.add(new Zoom());
        
        // Render modules
        modules.add(new Fullbright());
        modules.add(new Keystrokes());
        modules.add(new FpsDisplay());
        
        // Utility modules
        modules.add(new ChatTimestamps());
        
        // Performance modules
        modules.add(new FpsBooster());
        modules.add(new ParticleOptimizer());
    }
    
    public void registerModule(Module module) {
        if (!modules.contains(module)) {
            modules.add(module);
        }
    }
    
    public void toggleModule(Module module) {
        module.toggle();
    }
    
    public void toggleModule(String name) {
        Module module = getModule(name);
        if (module != null) {
            module.toggle();
        }
    }
    
    public Module getModule(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
    
    public List<Module> getModules() {
        return new ArrayList<>(modules);
    }
    
    public List<Module> getModulesByCategory(Module.Category category) {
        List<Module> categoryModules = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category) {
                categoryModules.add(module);
            }
        }
        return categoryModules;
    }
    
    public List<Module> getEnabledModules() {
        List<Module> enabled = new ArrayList<>();
        for (Module module : modules) {
            if (module.isEnabled()) {
                enabled.add(module);
            }
        }
        return enabled;
    }
    
    public void updateAll() {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onUpdate();
            }
        }
    }
    
    public void renderAll() {
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onRender();
            }
        }
    }
}
