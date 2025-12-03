package com.neko.menu;

import com.neko.RPGInventory;
import com.neko.section.Section;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class MenuHolder implements InventoryHolder {

    private final Inventory inventory;
    private final Section section;

    public MenuHolder(RPGInventory plugin, Section section, Player player) {
        this.inventory = section.toInventory(player, plugin);
        this.section = section;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public Section getSection() {
        return this.section;
    }
}
