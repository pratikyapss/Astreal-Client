# Astreal Client - Development Guide

## Architecture

Astreal Client uses a modular architecture with the following core systems:

### Module System
Each feature is a `Module` that extends the base class. Modules have:
- Lifecycle hooks (onEnable, onDisable, onUpdate, onRender)
- Settings system for configuration
- Category classification
- Keybind support

### Event System
Central event bus (`EventManager`) dispatches events:
- `ClientTickEvent` - Every game tick
- `RenderEvent` - HUD rendering
- `KeyPressEvent` - Key input

### Manager System
- **ModuleManager** - Registers and manages all modules
- **ConfigManager** - Handles config I/O
- **EventManager** - Event dispatching
- **CommandManager** - Command execution
- **GuiManager** - GUI rendering and management

### GUI System
- **GuiManager** - Main GUI coordinator
- **ClickGuiScreen** - Main GUI window
- **GuiPanel** - Category panels
- **GuiModuleButton** - Module toggles with settings

## Adding a Module

### Step 1: Create Module Class
```java
package com.astreal.modules.combat;

import com.astreal.modules.Module;
import com.astreal.modules.Setting;

public class MyFeature extends Module {
    private Setting<Float> mySetting;
    
    public MyFeature() {
        super("My Feature", "Description", Category.COMBAT);
        this.mySetting = addSetting(new Setting<>("My Setting", 1.0f, 0.0f, 10.0f));
    }
    
    @Override
    public void onEnable() {
        // Initialization code
    }
    
    @Override
    public void onDisable() {
        // Cleanup code
    }
    
    @Override
    public void onUpdate() {
        // Called every tick
        if (!isEnabled()) return;
        // Module logic here
    }
    
    @Override
    public void onRender() {
        // HUD rendering
    }
}
```

### Step 2: Register in ModuleManager
Edit `ModuleManager.java`:
```java
private void registerAllModules() {
    // ... existing modules ...
    modules.add(new MyFeature());
}
```

### Step 3: Build and Test
```bash
./gradlew build
./gradlew runClient
```

## Event Listening

Listen to events:
```java
EventManager eventManager = AstrealClient.getInstance().getEventManager();

eventManager.register(ClientTickEvent.class, event -> {
    // Handle tick event
});

eventManager.register(RenderEvent.class, event -> {
    DrawContext context = event.getDrawContext();
    // Render something
});
```

## Settings System

Create configurable settings:
```java
// Boolean setting
Setting<Boolean> enabled = addSetting(new Setting<>("Enabled", true));

// Numeric with range
Setting<Float> speed = addSetting(new Setting<>("Speed", 1.0f, 0.0f, 5.0f));
Setting<Integer> delay = addSetting(new Setting<>("Delay", 100, 0, 1000));

// String setting
Setting<String> mode = addSetting(new Setting<>("Mode", "Normal"));

// Listen for changes
enabled.setChangeCallback(value -> {
    if (value) {
        // Enable behavior
    }
});

// Get value
float speedValue = speed.getValue();
speed.setValue(2.0f);
```

## Rendering

Render HUD elements:
```java
@Override
public void onRender() {
    if (!isEnabled()) return;
    
    MinecraftClient client = MinecraftClient.getInstance();
    if (client.player == null) return;
    
    // Rendering would typically be done through GuiManager
    // or through HUD overlay systems
}
```

## Mixins

Bytecode modifications for optimization:
```java
@Mixin(SomeClass.class)
public class SomeMixin {
    
    @Inject(method = "someMethod", at = @At("HEAD"))
    private void onSomeMethod(CallbackInfo ci) {
        // Hook code
    }
    
    @Redirect(method = "expensiveMethod", at = @At("INVOKE", target = "..."))
    private void redirectExpensiveCall(...) {
        // More efficient implementation
    }
}
```

## Performance Tips

1. **Cache expensive calculations**
   ```java
   private long lastCacheTime = 0;
   private Object cachedValue = null;
   
   private Object getExpensiveValue() {
       long now = System.currentTimeMillis();
       if (now - lastCacheTime > 1000) {
           cachedValue = computeExpensiveValue();
           lastCacheTime = now;
       }
       return cachedValue;
   }
   ```

2. **Avoid allocations in hot paths**
   ```java
   // Bad
   @Override
   public void onUpdate() {
       List<Entity> entities = new ArrayList<>(); // Allocation every tick!
   }
   
   // Good
   private List<Entity> entityBuffer = new ArrayList<>();
   
   @Override
   public void onUpdate() {
       entityBuffer.clear();
       // Reuse buffer
   }
   ```

3. **Use proper null checks**
   ```java
   MinecraftClient client = MinecraftClient.getInstance();
   if (client.world == null || client.player == null) return;
   ```

## Code Style

- Use meaningful variable names
- Add comments for complex logic
- Keep methods focused and small
- Follow Java naming conventions
- Add Javadoc for public methods

## Testing

Run dev client:
```bash
./gradlew runClient
```

## Common Pitfalls

1. **Not checking if module is enabled**
   ```java
   // Always check at start of onUpdate/onRender
   if (!isEnabled()) return;
   ```

2. **Holding references that prevent GC**
   ```java
   // Bad: Keeps reference indefinitely
   private List<PlayerEntity> players = new ArrayList<>();
   
   // Good: Query when needed
   List<PlayerEntity> getPlayers() {
       return world.getPlayers();
   }
   ```

3. **Modifying collections while iterating**
   ```java
   // Bad
   for (Module m : modules) {
       if (condition) modules.remove(m);
   }
   
   // Good
   modules.removeIf(m -> condition);
   ```

## Debugging

Enable debug logging:
```java
Logger logger = new Logger("MyModule");
logger.debug("Debug message");
logger.info("Info message");
logger.warning("Warning message");
logger.error("Error message", exception);
```

Check logs in `.minecraft/astreal/logs/`

## Building for Release

```bash
./gradlew clean
./gradlew build
# JAR will be in build/libs/
```

## Submitting Changes

1. Fork repository
2. Create feature branch: `git checkout -b feature/my-feature`
3. Commit changes: `git commit -am 'Add my feature'`
4. Push to branch: `git push origin feature/my-feature`
5. Create Pull Request

---

Happy coding!
