package com.neko.stack;

import com.neko.RPGInventory;
import com.neko.menu.MenuHolder;
import com.neko.section.Step;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.entity.HumanEntity;

public class EquipStack extends PluginStack {

    public EquipStack(MenuHolder menuHolder, HumanEntity player, RPGInventory plugin, CustomStack clickedItem) {
        super(menuHolder, player, plugin, clickedItem);
    }

    @Override
    public void interact() {
        Step step = this.getMenuHolder().getPointedStep();

        if (step == null) return;
        if (step.isFullUnlocked(this.getPlayer())) step.equip(this.getPlayer(), this.getPlugin(), this.getMenuHolder().getSection());
        this.getPlayer().closeInventory();
    }
}
