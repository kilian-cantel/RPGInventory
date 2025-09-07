package com.neko;

import com.neko.section.Section;
import com.neko.section.Step;
import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RPGInventory extends JavaPlugin {

    private static RPGInventory instance;

    private static Section section;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        section = Section.loadSection();
    }

    @Override
    public void onDisable() {

    }

    @NotNull
    public static RPGInventory getInstance() {
        return instance;
    }

    public static void sendConsoleMessage(@NotNull Component msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    @NotNull
    public static Section getSection() {
        return section;
    }

    @NotNull
    public static CustomStack getMenuButton() {
        return CustomStack.getInstance(Config.MENUBUTTON.getString());
    }

    @NotNull
    public static CustomStack getLeftArrowButton() {
        return CustomStack.getInstance(Config.LEFTARROWBUTTON.getString());
    }

    @NotNull
    public static CustomStack getEquipButton() {
        return CustomStack.getInstance(Config.EQUIPBUTTON.getString());
    }

    @NotNull
    public static CustomStack getRightArrowButton() {
        return CustomStack.getInstance(Config.RIGHTARROWBUTTON.getString());
    }

    @NotNull
    public static CustomStack getUnlockedItemButton() {
        return CustomStack.getInstance(Config.UNLOCKEDITEMBUTTON.getString());
    }

    @NotNull
    public static CustomStack getLockedItemButton() {
        return CustomStack.getInstance(Config.LOCKEDITEMBUTTON.getString());
    }

    @NotNull
    public static Step getBestUnlockedStep(Player player) {
        Section section1 = section.cloneSection();

        while (section1.getBestUnlocked(player).equals(section1.getStep6())) {
            if (section1.getNextSection() == null) break;

            section1 = section1.getNextSection();
        }

        return section1.getBestUnlocked(player);
    }

    @Nullable
    public static Section getSection(int id) {
        Section section1 = section.cloneSection();
        while (section1.getId() != id) {
            if (section1.getNextSection() == null) return null;
            section1 = section1.getNextSection();
        }
        return section1;
    }
}

