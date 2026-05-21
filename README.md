# Astreal Client - Premium FPS Boosting & PvP Client

A high-performance, modern Minecraft 1.21.1 Fabric client inspired by Feather Client and Lunar Client. Astreal provides FPS optimization, PvP utilities, cosmetics, and a beautiful glassmorphism GUI.

## Features

### 🚀 FPS Optimization
- Aggressive rendering optimization
- Entity culling
- Particle optimization
- Memory management
- Chunk rendering tweaks
- Configurable FPS limiter

### ⚔️ PvP Utilities
- **Aim Assist** - Smooth aiming assistance
- **CPS Counter** - Real-time click tracking
- **Auto Sprint** - Automatic sprinting
- **Reach Visualizer** - See your attack reach
- **Combo Counter** - Track hit combos

### 🎨 Modern GUI
- Glassmorphism design
- Draggable panels
- Real-time settings
- Smooth animations
- Category-based organization
- Search functionality

### 🎮 Gameplay Features
- **Toggle Sprint** - Easy sprint toggling
- **Zoom** - Telescope zoom effect
- **Fullbright** - Brighten dark areas
- **Chat Timestamps** - Time-stamped messages
- **Keystrokes Display** - Show key presses
- **FPS Display** - Monitor frame rate

## Installation

### Requirements
- Java 21 or higher
- Minecraft 1.21.1
- Fabric Loader 0.16.0+

### Steps
1. Download the latest `.jar` from GitHub Releases
2. Place it in your `.minecraft/mods/` folder
3. Launch Minecraft with Fabric
4. Press `Right Shift` to open the ClickGUI

## Building from Source

```bash
# Clone repository
git clone https://github.com/yourusername/astreal-client.git
cd astreal-client

# Build project
./gradlew build

# Output jar location
build/libs/astreal-client-1.0.0.jar
```

## Configuration

Config files are saved in: `.minecraft/astreal/config/`

### settings.json
Main client settings including theme colors and animation speeds.

### Module Configs
Each module can be individually configured via the ClickGUI.

## GUI Controls

- **Right Shift** - Open/Close ClickGUI
- **Left Click Module** - Toggle on/off
- **Right Click Module** - Show/hide settings
- **Drag Panel Header** - Move panels
- **Mouse Wheel** - Scroll settings
- **ESC** - Close GUI

## Modules

### Combat
- Aim Assist
- Auto Sprint
- CPS Counter

### Movement
- Toggle Sprint
- Zoom

### Render
- Fullbright
- Keystrokes
- FPS Display

### Utility
- Chat Timestamps

### Performance
- FPS Booster
- Particle Optimizer

## Commands

- `.help` - Show all commands
- `.toggle <module>` - Toggle a module
- `.bind <module> <key>` - Bind a key
- `.config` - Open config manager

## Theming

Edit the color scheme in `settings.json`:
```json
{
  "themes": {
    "primaryColor": "#FF00FF",
    "secondaryColor": "#1A1A2E",
    "accentColor": "#16C784"
  }
}
```

## Development

### Project Structure
```
src/main/java/com/astreal/
├── client/          # Main client class
├── modules/         # Feature modules
│   ├── combat/
│   ├── movement/
│   ├── render/
│   ├── utility/
│   └── performance/
├── gui/             # GUI system
├── hud/             # HUD rendering
├── managers/        # System managers
├── events/          # Event system
├── rendering/       # Render utilities
├── commands/        # Command system
├── configs/         # Config system
├── utils/           # Utilities
├── cosmetics/       # Cosmetic system
└── mixin/           # Bytecode modifications
```

### Creating a Module

```java
public class MyModule extends Module {
    public MyModule() {
        super("My Module", "Description", Category.UTILITY);
    }
    
    @Override
    public void onEnable() {
        // Called when enabled
    }
    
    @Override
    public void onDisable() {
        // Called when disabled
    }
    
    @Override
    public void onUpdate() {
        // Called every tick
    }
    
    @Override
    public void onRender() {
        // Called when rendering HUD
    }
}
```

## GitHub Actions

Automatic builds on push thanks to `.github/workflows/build.yml`:
- Triggers on push to main/develop
- Builds with Java 21
- Uploads JAR as artifact
- Creates releases on tags

## Performance

Astreal Client improves FPS by:
- Optimizing chunk rendering
- Reducing entity calculations
- Limiting particles
- Caching compiled shaders
- Reducing garbage collection pressure

Expected FPS improvement: 30-60% on low-end hardware

## License

MIT License - See LICENSE file

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## Disclaimer

This client is for personal use only. Use responsibly on servers that allow modification clients. The authors are not responsible for any ban or consequences resulting from use.

## Credits

- Inspired by Feather Client
- Built with Fabric API
- Community contributions

---

**Astreal Client** - Performance at its Peak
