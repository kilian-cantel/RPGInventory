package com.neko.command;

import com.neko.RPGInventory;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReloadCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender.hasPermission("rpginventory.reload")) {
            RPGInventory.getInstance().reload();
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>Plugin RPGInventory reloaded!"));
            return true;
        }
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<red>You don't have permission to do this!"));

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        return List.of();
    }
}
