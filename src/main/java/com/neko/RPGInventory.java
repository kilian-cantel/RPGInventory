package com.neko;

import com.neko.command.ReloadCommand;
import com.neko.listener.inventoryClickEvent;
import com.neko.menu.MenuHolder;
import com.neko.section.Section;
import com.neko.section.Step;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Objects;
import java.util.logging.Logger;

public class RPGInventory extends JavaPlugin {

    private static RPGInventory instance;

    private Section section;
    private YamlConfiguration sectionConfiguration;
    private final Logger LOGGER = getLogger();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        if (!Config.ENABLE.getBoolean()) {
            LOGGER.warning("Plugin RPGInventory is disabled in config.yml! The plugin will not be loaded anymore!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        this.sectionConfiguration = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config/sections.yml"));
        instance = this;

        this.section = Section.loadSection(this.sectionConfiguration, LOGGER);

        Objects.requireNonNull(this.getCommand("reload")).setExecutor(new ReloadCommand());
        this.getServer().getPluginManager().registerEvents(new inventoryClickEvent(this), this);

        LOGGER.info("Plugin RPGInventory enabled!");
    }

    @Override
    public void onDisable() {
        LOGGER.info("Plugin RPGInventory disabled!");
    }

    @NotNull
    public static RPGInventory getInstance() {
        return instance;
    }

    @NotNull
    public YamlConfiguration getSectionConfiguration() {
        return this.sectionConfiguration;
    }

    @NotNull
    public Section getSection() {
        return section;
    }

    @NotNull
    public CustomStack getMenuButton() {
        return CustomStack.getInstance(Config.MENUBUTTON.getString());
    }

    public void interactMenuButton(Player player) {
        this.openInventory(player);
    }

    @NotNull
    public CustomStack getLeftArrowButton() {
        return CustomStack.getInstance(Config.LEFTARROWBUTTON.getString());
    }

    public void interactLeftArrowButton(Player player, Section section) {
        Section leftSection = getLeftSection(section);

        if (leftSection == null) return;

        openInventory(player, leftSection);
    }

    @NotNull
    public CustomStack getEquipButton() {
        return CustomStack.getInstance(Config.EQUIPBUTTON.getString());
    }

    public void interactEquipButton(Player player, Section section, CustomStack stack) {
        Step step = section.getFromHeader(stack);

        if (step == null) return;
        if (step.isFullUnlocked(player)) step.equip(player);
    }

    @NotNull
    public CustomStack getRightArrowButton() {
        return CustomStack.getInstance(Config.RIGHTARROWBUTTON.getString());
    }

    public void interactRightArrowButton(Player player, Section section) {
        Section rightSection = section.getNextSection();
        if (rightSection == null) return;
        openInventory(player, rightSection);
    }

    @NotNull
    public CustomStack getUnlockedItemButton() {
        return CustomStack.getInstance(Config.UNLOCKEDITEMBUTTON.getString());
    }

    @NotNull
    public CustomStack getLockedItemButton() {
        return CustomStack.getInstance(Config.LOCKEDITEMBUTTON.getString());
    }

    private void setSection(Section section) {
        this.section = section;
    }

    @Nullable
    public Step getBestUnlockedStep(Player player) {
        Section section1 = section.cloneSection();

        while (Objects.equals(section1.getBestUnlocked(player), section1.getStep6())) {
            if (section1.getNextSection() == null) break;

            section1 = section1.getNextSection();
        }

        return section1.getBestUnlocked(player);
    }

    @Nullable
    public Section getSection(int id) {
        Section section1 = section.cloneSection();
        while (section1.getId() != id) {
            if (section1.getNextSection() == null) return null;
            section1 = section1.getNextSection();
        }
        return section1;
    }

    public Logger getLOGGER() {
        return LOGGER;
    }

    public void reload() {
        this.reloadConfig();
        this.sectionConfiguration = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "config/sections.yml"));
        this.setSection(Section.loadSection(this.sectionConfiguration, LOGGER));
        this.LOGGER.info("Plugin RPGInventory reloaded!");
    }

    public void openInventory(@NotNull Player player) {
        this.openInventory(player, this.section);
    }

    public void openInventory(@NotNull Player player, Section section) {
        Inventory menu = section.toInventory(player, this);
        if (menu == null) return;

        player.openInventory(menu);
    }

    @Nullable
    public Section getLeftSection(Section section) {
        Section prec = null;
        Section curr = this.section;
        if (this.section.getId() == section.getId()) return null;

        while(curr.getId() != section.getId()) {
            if (curr.getNextSection() == null) return null;
            prec = curr;
            curr = curr.getNextSection();
        }
        return prec;
    }

    public void interact(CustomStack stack, Player player, MenuHolder holder) {
        if (stack.equals(getMenuButton())) interactMenuButton(player);

        Section section = holder.getSection();
        if (stack.equals(getLeftArrowButton())) interactLeftArrowButton(player, section);
        if (stack.equals(getRightArrowButton())) interactRightArrowButton(player, section);
        if (stack.equals(getEquipButton())) interactEquipButton(player, section, stack);
    }
}

