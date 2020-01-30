package me.ishift.epicguard.bukkit.util.misc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomConfig {
    private File file;
    private FileConfiguration config;

    public CustomConfig(File file) {
        this.file = file;
    }

    public boolean exists() {
        return this.file.exists();
    }

    public void create() {
        try {
            if (this.file.createNewFile()) {
                System.out.println("[EpicGuard] Created new configuration file: " + this.file.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public File getFile() {
        return this.file;
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
