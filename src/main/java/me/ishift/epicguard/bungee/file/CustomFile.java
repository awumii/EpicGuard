package me.ishift.epicguard.bungee.file;

import me.ishift.epicguard.universal.util.Logger;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;

public class CustomFile {
    public File file;
    public Configuration configuration;

    public CustomFile(String file) {
        this.file = new File(file);
        try {
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean exist() {
        return this.file.exists();
    }

    public void create() {
        try {
            if (this.file.createNewFile()) {
                Logger.debug("Created new file: " + this.file.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return this.configuration;
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

