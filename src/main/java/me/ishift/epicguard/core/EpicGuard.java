package me.ishift.epicguard.core;

import me.ishift.epicguard.core.config.Configuration;
import me.ishift.epicguard.core.config.MessagesConfiguration;
import me.ishift.epicguard.core.manager.GeoManager;
import me.ishift.epicguard.core.manager.StorageManager;
import me.ishift.epicguard.core.util.ConfigHelper;

import java.io.File;
import java.util.logging.Logger;

public class EpicGuard {
    private final Logger logger;
    private final StorageManager storageManager;
    private final GeoManager geoManager;

    private final Configuration config;
    private final MessagesConfiguration messages;

    private boolean attack;
    private int connectionPerSecond;

    public EpicGuard(Logger logger) {
        this.logger = logger;
        logger.info("███████╗██████╗ ██╗ ██████╗ ██████╗ ██╗   ██╗ █████╗ ██████╗ ██████╗");
        logger.info("██╔════╝██╔══██╗██║██╔════╝██╔════╝ ██║   ██║██╔══██╗██╔══██╗██╔══██╗");
        logger.info("█████╗  ██████╔╝██║██║     ██║  ███╗██║   ██║███████║██████╔╝██║  ██║");
        logger.info("██╔══╝  ██╔═══╝ ██║██║     ██║   ██║██║   ██║██╔══██║██╔══██╗██║  ██║");
        logger.info("███████╗██║     ██║╚██████╗╚██████╔╝╚██████╔╝██║  ██║██║  ██║██████╔╝");
        logger.info("╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝");
        logger.info("EpicGuard v5-NEON is starting up...");

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
        logger.info("EpicGuard v5-NEON finished startup successfully.");
    }

    public void shutdown() {
        logger.info("EpicGuard v5-NEON is shutting down...");
        this.storageManager.save();
    }

    public Logger getLogger() {
        return this.logger;
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
