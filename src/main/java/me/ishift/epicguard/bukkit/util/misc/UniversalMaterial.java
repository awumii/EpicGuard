package me.ishift.epicguard.bukkit.util.misc;

import me.ishift.epicguard.bukkit.util.server.Reflection;
import org.bukkit.Material;

public enum UniversalMaterial {
    CLOCK("WATCH", "CLOCK"),
    EXP_BOTTLE("EXP_BOTTLE", "EXPERIENCE_BOTTLE"),
    BOOK_AND_QUILL("BOOK_AND_QUILL", "WRITABLE_BOOK"),
    CRAFTING("WORKBENCH", "CRAFTING_TABLE"),
    NETHER_BRICK("NETHER_BRICK_ITEM", "NETHER_BRICK"),
    FENCE_GATE("FENCE_GATE", "OAK_FENCE_GATE");

    private String legacy;
    private String current;

    UniversalMaterial(String legacy, String current) {
        this.legacy = legacy;
        this.current = current;
    }

    public String getCurrent() {
        return this.current;
    }

    public String getLegacy() {
        return this.legacy;
    }

    public static Material get(UniversalMaterial material) {
        if (Reflection.isOldVersion()) {
            return Material.getMaterial(material.getLegacy());
        }
        return Material.getMaterial(material.getCurrent());
    }
}
