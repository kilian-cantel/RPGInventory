package com.neko;

import com.neko.section.Section;
import com.neko.section.Step;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

public class PluginPlayer {

    @Nullable
    public static Step getStep(Player player) {
        PersistentDataContainer data = player.getPersistentDataContainer();
        String stepString = data.get(new NamespacedKey(RPGInventory.getInstance(), "step"), PersistentDataType.STRING);
        return Section.getStep(stepString);
    }
}
