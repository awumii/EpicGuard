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
            Logger.throwException(e);
        }
    }

    public boolean exist() {
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

    public Configuration getConfig() {
        return configuration;
    }

    public void save() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, this.file);
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}

