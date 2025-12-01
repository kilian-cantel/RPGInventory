package com.neko.section;

import com.neko.RPGInventory;
import dev.lone.itemsadder.api.CustomStack;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Step {

    private CustomStack header;
    private CustomStack helmet;
    private CustomStack chestplate;
    private CustomStack leggings;
    private CustomStack boots;

    public Step(@NotNull CustomStack header, @NotNull CustomStack helmet, @NotNull CustomStack chestplate, @NotNull CustomStack leggings, @NotNull CustomStack boots) {
        this.header = header;
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    @NotNull
    public CustomStack getHeader() {
        return header;
    }

    @NotNull
    public CustomStack getHelmet() {
        return helmet;
    }

    @NotNull
    public CustomStack getChestplate() {
        return chestplate;
    }

    @NotNull
    public CustomStack getLeggings() {
        return leggings;
    }

    @NotNull
    public CustomStack getBoots() {
        return boots;
    }

    public void setHeader(@NotNull CustomStack header) {
        this.header = header;
    }

    public void setHelmet(@NotNull CustomStack helmet) {
        this.helmet = helmet;
    }

    public void setChestplate(@NotNull CustomStack chestplate) {
        this.chestplate = chestplate;
    }

    public void setLeggings(@NotNull CustomStack leggings) {
        this.leggings = leggings;
    }

    public void setBoots(@NotNull CustomStack boots) {
        this.boots = boots;
    }

    @Nullable
    public static Step loadStep(int sectionId, int stepId, FileConfiguration config) {
        if (config.contains(sectionId + ".steps." + stepId)) {
            String headerString = config.getString(sectionId + ".steps." + stepId + ".header");
            if (headerString == null) return null;

            String helmetString = config.getString(sectionId + ".steps." + stepId + ".helmet");
            if (helmetString == null) return null;

            String chestplateString = config.getString(sectionId + ".steps." + stepId + ".chestplate");
            if (chestplateString == null) return null;

            String leggingsString = config.getString(sectionId + ".steps." + stepId + ".leggings");
            if (leggingsString == null) return null;

            String bootsString = config.getString(sectionId + ".steps." + stepId + ".boots");
            if (bootsString == null) return null;

            CustomStack header = CustomStack.getInstance(headerString);
            if (header == null) return null;

            CustomStack helmet = CustomStack.getInstance(helmetString);
            if (helmet == null) return null;

            CustomStack chestplate = CustomStack.getInstance(chestplateString);
            if (chestplate == null) return null;

            CustomStack leggings = CustomStack.getInstance(leggingsString);
            if (leggings == null) return null;

            CustomStack boots = CustomStack.getInstance(bootsString);
            if (boots == null) return null;

            return new Step(
                    header,
                    helmet,
                    chestplate,
                    leggings,
                    boots
            );
        }
        return null;
    }

    @Nullable
    public Inventory setStepToInventory(Inventory inv, int pointer, Player player) {
        ItemStack locked = RPGInventory.getLockedItemButton().getItemStack();
        ItemStack unlocked = RPGInventory.getUnlockedItemButton().getItemStack();

        inv.setItem(pointer, this.getHeader().getItemStack());

        if (!player.hasPermission("rpginventory." + this.getHelmet().getNamespacedID()))
            inv.setItem(pointer + 9, locked);
        else
            inv.setItem(pointer + 9, unlocked);

        if (!player.hasPermission("rpginventory." + this.getChestplate().getNamespacedID()))
            inv.setItem(pointer + 18, locked);
        else
            inv.setItem(pointer + 18, unlocked);

        if (!player.hasPermission("rpginventory." + this.getLeggings().getNamespacedID()))
            inv.setItem(pointer + 27, locked);
        else
            inv.setItem(pointer + 27, unlocked);

        if (!player.hasPermission("rpginventory." + this.getBoots().getNamespacedID()))
            inv.setItem(pointer + 36, locked);
        else
            inv.setItem(pointer + 36, unlocked);

        return inv;
    }

    public boolean isFullUnlocked(Player player) {
        return !player.hasPermission("rpginventory." + this.helmet.getNamespacedID()) ||
                !player.hasPermission("rpginventory." + this.chestplate.getNamespacedID()) ||
                !player.hasPermission("rpginventory." + this.leggings.getNamespacedID()) ||
                !player.hasPermission("rpginventory." + this.boots.getNamespacedID());
    }
}
