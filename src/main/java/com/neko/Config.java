package com.neko;

import org.bukkit.configuration.file.FileConfiguration;

public enum Config {

    ENABLE(true, "enable"),
    MENUBUTTON("menuIcon", "menu_button"),
    LEFTARROWBUTTON("leftArrowIcon", "left_arrow_button"),
    EQUIPBUTTON("equipIcon", "equip_button"),
    RIGHTARROWBUTTON("rightArrowIcon", "right_arrow_button"),
    UNLOCKEDITEMBUTTON("unlockedItemIcon", "unlocked_item_button"),
    LOCKEDITEMBUTTON("lockedItemIcon", "locked_item_button"),
    UNDEFINEDITEM("undefinedItem", "undefined_item");

    private final Object defaultValue;
    private final String path;

    private static final FileConfiguration config = RPGInventory.getInstance().getConfig();

    Config(Object defaultValue, String path) {
        this.defaultValue = defaultValue;
        this.path = path;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public String getPath() {
        return this.path;
    }

    public Object get() {
        Object result = config.get(this.path);

        if (result == null) {
            config.set(this.path, this.defaultValue);
            return this.defaultValue;
        }

        return result;
    }

    public String getString() {
        return String.valueOf(this.get());
    }

    public int getInt() {
        return Integer.parseInt(this.getString());
    }

    public boolean getBoolean() {
        return Boolean.parseBoolean(this.getString());
    }
}
