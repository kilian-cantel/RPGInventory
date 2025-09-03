package com.neko;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGInventory extends JavaPlugin {

    private static RPGInventory instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
    }

    @Override
    public void onDisable() {

    }

    public static RPGInventory getInstance() {
        return instance;
    }

    public static void sendConsoleMessage(Component msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }
}

