package me.ishift.epicguard.bukkit.object;

import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CustomFile {
    public File file;
    public FileConfiguration configuration;

    public CustomFile(String file) {
        this.file = new File(file);
        this.configuration = YamlConfiguration.loadConfiguration(this.file);
    }

    public boolean isExisting() {
        return file.exists();
    }

    public void create() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    public FileConfiguration getConfig() {
        return configuration;
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}
