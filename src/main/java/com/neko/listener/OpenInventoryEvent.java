package com.neko.listener;

import com.neko.RPGInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;

public class OpenInventoryEvent implements Listener {

    @EventHandler
    public void onOpenInventory(org.bukkit.event.inventory.InventoryOpenEvent event) {
        if (event.getInventory().getType().equals(InventoryType.PLAYER)) {
            if (event.getPlayer() instanceof Player player) {
                event.setCancelled(true);
                RPGInventory.getInstance().openInventory(player);
            }
        }
    }

}
