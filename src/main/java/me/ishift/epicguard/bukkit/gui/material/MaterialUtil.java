package me.ishift.epicguard.bukkit.gui.material;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.util.nms.Reflection;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MaterialUtil {
    private static FileConfiguration configuration;

    public static Material get(UniversalMaterial material) {
        if (Reflection.isOldVersion()) {
            return Material.getMaterial(getString("old." + material.getAlias()));
        }
        return Material.getMaterial(getString("new." + material.getAlias()));
    }

    private static String getString(String path) {
        return configuration.getString(path);
    }

    public static void init() {
        try {
            final File file = new File(GuardBukkit.getInstance().getDataFolder() + "/data/material.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            configuration = YamlConfiguration.loadConfiguration(file);
            configuration.set("old.clock", "WATCH");
            configuration.set("new.clock", "CLOCK");
            configuration.set("old.exp_bottle", "EXP_BOTTLE");
            configuration.set("new.exp_bottle", "EXPERIENCE_BOTTLE");
            configuration.set("old.book_and_quill", "BOOK_AND_QUILL");
            configuration.set("new.book_and_quill", "WRITABLE_BOOK");
            configuration.set("old.crafting", "WORKBENCH");
            configuration.set("new.crafting", "CRAFTING_TABLE");
            configuration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
