package com.neko.listener;

import com.neko.RPGInventory;
import com.neko.menu.MenuHolder;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class inventoryClickEvent implements Listener {

    private final RPGInventory plugin;

    public inventoryClickEvent(RPGInventory plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MenuHolder holder && event.getWhoClicked() instanceof Player player) {
            this.plugin.interact(CustomStack.byItemStack(event.getCurrentItem()), player, holder);
        }
    }
}
