package com.neko.listener;

import com.neko.RPGInventory;
import com.neko.menu.MenuHolder;
import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.FontImages.TexturedInventoryWrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;

import java.lang.reflect.InvocationTargetException;

public class InventoryClickEvent implements Listener {

    private final RPGInventory plugin;

    public InventoryClickEvent(RPGInventory plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(org.bukkit.event.inventory.InventoryClickEvent event)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (event.getInventory().getHolder() instanceof MenuHolder holder && event.getCurrentItem() != null) {
            Pair<CustomStack, String> data = this.plugin.toCustomStack(event.getCurrentItem());
            if (data == null) return;
            RPGInventory.CustomStackType type = RPGInventory.CustomStackType.valueOf(data.getRight());
            type.interact(holder, event.getWhoClicked(), this.plugin, data.getLeft());
            event.setCancelled(true);
        }

        if (event.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
            event.setCancelled(true);
        }
    }
}