package me.ishift.epicguard.core;

import me.ishift.epicguard.core.config.Configuration;
import me.ishift.epicguard.core.config.MessagesConfiguration;
import me.ishift.epicguard.core.manager.GeoManager;
import me.ishift.epicguard.core.manager.StorageManager;
import me.ishift.epicguard.core.util.ConfigHelper;

import java.io.File;

public class EpicGuard {
    private final StorageManager storageManager;
    private final GeoManager geoManager;

    private final Configuration config;
    private final MessagesConfiguration messages;

    private boolean attack;
    private int connectionPerSecond;

    public EpicGuard() {
        File dataFolder = new File("plugins/EpicGuard");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File configurationFile = new File(dataFolder, "config.yml");
        File messagesFile = new File(dataFolder, "messages.yml");
        this.config = ConfigHelper.loadConfig(configurationFile, Configuration.class);
        this.messages = ConfigHelper.loadConfig(messagesFile, MessagesConfiguration.class);

        this.storageManager = new StorageManager();
        this.geoManager = new GeoManager(this);
    }

    public Configuration getConfig() {
        return this.config;
    }

    public MessagesConfiguration getMessages() {
        return this.messages;
    }

    public GeoManager getGeoManager() {
        return this.geoManager;
    }

    public StorageManager getStorageManager() {
        return this.storageManager;
    }

    public boolean isAttack() {
        return this.attack;
    }

    public void setAttack(boolean attack) {
        this.attack = attack;
    }

    public int getConnectionPerSecond() {
        return this.connectionPerSecond;
    }

    public void setConnectionPerSecond(int connectionPerSecond) {
        this.connectionPerSecond = connectionPerSecond;
    }
}
