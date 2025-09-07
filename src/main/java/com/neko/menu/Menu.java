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

    @NotNull
    private static Inventory sectionToInventory(Section section, Player player) {
        Inventory inv = Bukkit.createInventory(player, 54, MiniMessage.miniMessage().deserialize(section.getTitle()));

        inv.setItem(9, RPGInventory.getMenuButton().getItemStack());
        inv.setItem(10, RPGInventory.getLeftArrowButton().getItemStack());
        inv.setItem(11, RPGInventory.getEquipButton().getItemStack());
        inv.setItem(12, RPGInventory.getRightArrowButton().getItemStack());

        inv = Step.setStepToInventory(section.getStep1(), inv, 13, player);
        inv = Step.setStepToInventory(section.getStep2(), inv, 14, player);
        inv = Step.setStepToInventory(section.getStep3(), inv, 15, player);
        inv = Step.setStepToInventory(section.getStep4(), inv, 16, player);
        inv = Step.setStepToInventory(section.getStep5(), inv, 17, player);
        inv = Step.setStepToInventory(section.getStep6(), inv, 18, player);

        Step playerStep = PluginPlayer.getStep(player);

        if (playerStep != null) {
            inv.setItem(20, playerStep.getHelmet().getItemStack());
            inv.setItem(29, playerStep.getChestplate().getItemStack());
            inv.setItem(38, playerStep.getLeggings().getItemStack());
            inv.setItem(47, playerStep.getBoots().getItemStack());
        }
        return inv;
    }

}
