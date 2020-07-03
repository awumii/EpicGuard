package me.ishift.epicguard.core;

import me.ishift.epicguard.core.manager.GeoManager;
import me.ishift.epicguard.core.manager.StorageManager;
import me.ishift.epicguard.core.util.ConfigHelper;

import java.io.File;

public class EpicGuard {
    private final Configuration config;
    private final StorageManager storageManager;
    private final GeoManager geoManager;

    private boolean attack;

    public EpicGuard() {
        File dataFolder = new File("plugins/EpicGuard");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File configurationFile = new File(dataFolder, "config.yml");
        this.config = ConfigHelper.loadConfig(configurationFile, Configuration.class);

        this.storageManager = new StorageManager();
        this.geoManager = new GeoManager(this);
    }

    public Configuration getConfig() {
        return this.config;
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
}
