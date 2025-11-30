package com.neko;

import com.neko.section.Section;
import com.neko.section.Step;
import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;

public class RPGInventory extends JavaPlugin {

    private static RPGInventory instance;

    private static Section section;
    private YamlConfiguration sectionConfiguration;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.sectionConfiguration = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config/sections.yml"));
        instance = this;

        section = Section.loadSection(this.sectionConfiguration);
    }

    @Override
    public void onDisable() {

    }

    @NotNull
    public static RPGInventory getInstance() {
        return instance;
    }

    @NotNull
    public YamlConfiguration getSectionConfiguration() {
        return this.sectionConfiguration;
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

    @Nullable
    public static Step getBestUnlockedStep(Player player) {
        Section section1 = section.cloneSection();

        while (Objects.equals(section1.getBestUnlocked(player), section1.getStep6())) {
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

