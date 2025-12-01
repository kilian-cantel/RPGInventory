package com.neko.menu;

import com.neko.PluginPlayer;
import com.neko.RPGInventory;
import com.neko.section.Section;
import com.neko.section.Step;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Menu {

    private Section section;
    private String subCommand;

    public Menu(Section section, String subCommand) {
        this.section = section;
        this.subCommand = subCommand;
    }

    public Section getSection() {
        return section;
    }

    public String getSubCommand() {
        return subCommand;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public void setSubCommand(String subCommand) {
        this.subCommand = subCommand;
    }

    @Nullable
    private Inventory sectionToInventory(Player player, RPGInventory plugin) {
        return this.section.toInventory(player, plugin);
    }

}
