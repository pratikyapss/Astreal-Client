package com.astreal.managers;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Manages configuration saving and loading
 * Stores all module settings and client configuration
 */
public class ConfigManager {
    private static final Path CONFIG_DIR = Paths.get("astreal/config");
    private static final Path SETTINGS_FILE = CONFIG_DIR.resolve("settings.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    private JsonObject config;
    
    public ConfigManager() {
        config = new JsonObject();
        ensureConfigDirectoryExists();
    }
    
    private void ensureConfigDirectoryExists() {
        try {
            if (!Files.exists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadConfig() {
        try {
            if (Files.exists(SETTINGS_FILE)) {
                String content = new String(Files.readAllBytes(SETTINGS_FILE));
                config = GSON.fromJson(content, JsonObject.class);
            } else {
                createDefaultConfig();
                saveConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
            createDefaultConfig();
        }
    }
    
    public void saveConfig() {
        try {
            Files.write(SETTINGS_FILE, GSON.toJson(config).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void createDefaultConfig() {
        JsonObject themes = new JsonObject();
        themes.addProperty("primaryColor", "#FF00FF");
        themes.addProperty("secondaryColor", "#1A1A2E");
        themes.addProperty("accentColor", "#16C784");
        
        config.add("themes", themes);
        config.addProperty("guiScale", 1.0f);
        config.addProperty("animationSpeed", 1.0f);
        config.addProperty("notificationDuration", 3000);
    }
    
    public String getString(String key, String defaultValue) {
        if (config.has(key)) {
            return config.get(key).getAsString();
        }
        return defaultValue;
    }
    
    public void setString(String key, String value) {
        config.addProperty(key, value);
    }
    
    public int getInt(String key, int defaultValue) {
        if (config.has(key)) {
            return config.get(key).getAsInt();
        }
        return defaultValue;
    }
    
    public void setInt(String key, int value) {
        config.addProperty(key, value);
    }
    
    public float getFloat(String key, float defaultValue) {
        if (config.has(key)) {
            return config.get(key).getAsFloat();
        }
        return defaultValue;
    }
    
    public void setFloat(String key, float value) {
        config.addProperty(key, value);
    }
    
    public boolean getBoolean(String key, boolean defaultValue) {
        if (config.has(key)) {
            return config.get(key).getAsBoolean();
        }
        return defaultValue;
    }
    
    public void setBoolean(String key, boolean value) {
        config.addProperty(key, value);
    }
    
    public JsonObject getConfig() {
        return config;
    }
}
