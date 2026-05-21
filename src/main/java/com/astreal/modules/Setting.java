package com.astreal.modules;

import java.util.function.Consumer;

/**
 * Generic setting class for module configuration
 */
public class Setting<T> {
    private String name;
    private T value;
    private T defaultValue;
    private T min;
    private T max;
    private Consumer<T> changeCallback;
    private SettingType type;
    
    public enum SettingType {
        BOOLEAN,
        INTEGER,
        FLOAT,
        DOUBLE,
        STRING,
        ENUM,
        COLOR
    }
    
    public Setting(String name, T defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.type = inferType(defaultValue);
    }
    
    public Setting(String name, T defaultValue, T min, T max) {
        this(name, defaultValue);
        this.min = min;
        this.max = max;
    }
    
    private SettingType inferType(T value) {
        if (value instanceof Boolean) return SettingType.BOOLEAN;
        if (value instanceof Integer) return SettingType.INTEGER;
        if (value instanceof Float) return SettingType.FLOAT;
        if (value instanceof Double) return SettingType.DOUBLE;
        if (value instanceof String) return SettingType.STRING;
        if (value instanceof Enum) return SettingType.ENUM;
        return SettingType.STRING;
    }
    
    public String getName() {
        return name;
    }
    
    public T getValue() {
        return value;
    }
    
    public void setValue(T value) {
        T oldValue = this.value;
        this.value = value;
        if (changeCallback != null) {
            changeCallback.accept(value);
        }
    }
    
    public T getDefaultValue() {
        return defaultValue;
    }
    
    public T getMin() {
        return min;
    }
    
    public T getMax() {
        return max;
    }
    
    public SettingType getType() {
        return type;
    }
    
    public void setChangeCallback(Consumer<T> callback) {
        this.changeCallback = callback;
    }
    
    public void reset() {
        setValue(defaultValue);
    }
}

