package com.astreal.managers;

import java.util.*;
import net.minecraft.client.MinecraftClient;

/**
 * Manages command execution and registration
 * Handles prefix-based command system
 */
public class CommandManager {
    private String prefix = ".";
    private Map<String, Command> commands = new HashMap<>();
    
    public CommandManager() {
        registerDefaultCommands();
    }
    
    private void registerDefaultCommands() {
        registerCommand("help", "Shows all available commands", args -> {
            String output = "§7Available Commands:\n";
            for (String name : commands.keySet()) {
                output += "§f" + prefix + name + "\n";
            }
            sendMessage(output);
            return true;
        });
        
        registerCommand("toggle", "Toggle a module", args -> {
            if (args.length < 2) {
                sendMessage("§cUsage: .toggle <module>");
                return false;
            }
            return true;
        });
        
        registerCommand("bind", "Bind a key to a module", args -> {
            if (args.length < 3) {
                sendMessage("§cUsage: .bind <module> <key>");
                return false;
            }
            return true;
        });
        
        registerCommand("config", "Manage configurations", args -> {
            sendMessage("§7Config manager opened");
            return true;
        });
    }
    
    public void registerCommand(String name, String description, CommandExecutor executor) {
        commands.put(name.toLowerCase(), new Command(name, description, executor));
    }
    
    public void executeCommand(String input) {
        if (!input.startsWith(prefix)) {
            return;
        }
        
        String[] parts = input.substring(1).split(" ");
        if (parts.length == 0) {
            return;
        }
        
        String commandName = parts[0].toLowerCase();
        String[] args = parts;
        
        Command command = commands.get(commandName);
        if (command != null) {
            try {
                command.executor.execute(args);
            } catch (Exception e) {
                sendMessage("§cError executing command: " + e.getMessage());
            }
        } else {
            sendMessage("§cUnknown command: " + commandName);
        }
    }
    
    public void handleInput() {
        // This would be called from input handling
    }
    
    private void sendMessage(String message) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.sendMessage(net.minecraft.text.Text.of(message));
        }
    }
    
    public String getPrefix() {
        return prefix;
    }
    
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    @FunctionalInterface
    public interface CommandExecutor {
        boolean execute(String[] args);
    }
    
    private static class Command {
        String name;
        String description;
        CommandExecutor executor;
        
        Command(String name, String description, CommandExecutor executor) {
            this.name = name;
            this.description = description;
            this.executor = executor;
        }
    }
}

