package me.ishift.epicguard.core;

import me.ishift.epicguard.core.config.PluginConfiguration;
import me.ishift.epicguard.core.config.MessagesConfiguration;
import me.ishift.epicguard.core.manager.GeoManager;
import me.ishift.epicguard.core.manager.NotificationManager;
import me.ishift.epicguard.core.manager.StorageManager;
import me.ishift.epicguard.core.user.UserManager;
import me.ishift.epicguard.core.util.ConfigHelper;
import me.ishift.epicguard.core.util.LogFilter;
import me.ishift.epicguard.core.util.Notificator;

import java.io.File;
import java.util.logging.Logger;

public class EpicGuard {
    private final Logger logger;
    private final StorageManager storageManager;
    private final GeoManager geoManager;
    private final UserManager userManager;
    private final NotificationManager notificationManager;

    private PluginConfiguration config;
    private MessagesConfiguration messages;

    private boolean attack;
    private int connectionPerSecond;

    public EpicGuard(Logger logger, Notificator notificator) {
        this.logger = logger;
        logger.info("███████╗██████╗ ██╗ ██████╗ ██████╗ ██╗   ██╗ █████╗ ██████╗ ██████╗");
        logger.info("██╔════╝██╔══██╗██║██╔════╝██╔════╝ ██║   ██║██╔══██╗██╔══██╗██╔══██╗");
        logger.info("█████╗  ██████╔╝██║██║     ██║  ███╗██║   ██║███████║██████╔╝██║  ██║");
        logger.info("██╔══╝  ██╔═══╝ ██║██║     ██║   ██║██║   ██║██╔══██║██╔══██╗██║  ██║");
        logger.info("███████╗██║     ██║╚██████╗╚██████╔╝╚██████╔╝██║  ██║██║  ██║██████╔╝");
        logger.info("╚══════╝╚═╝     ╚═╝ ╚═════╝ ╚═════╝  ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝");
        logger.info("EpicGuard v5 is starting up...");

        this.reloadConfig();
        this.storageManager = new StorageManager();
        this.userManager = new UserManager();
        this.notificationManager = new NotificationManager(this, notificator);
        this.geoManager = new GeoManager(this);

        try {
            new LogFilter(this.config.consoleFilter).register();
        } catch (Exception e) {
            logger.warning("LogFilter can't be enabled, because log4j is not found. If you are running on BungeeCord, consider a switch to Waterfall.");
        }

        logger.info("EpicGuard v5 finished startup successfully.");
    }

    public void shutdown() {
        this.storageManager.save();
    }

    public String getVersion() {
        return "5.0.0";
    }

    public void reloadConfig() {
        File dataFolder = new File("plugins/EpicGuard");
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        File configurationFile = new File(dataFolder, "config.yml");
        File messagesFile = new File(dataFolder, "messages.yml");
        this.config = ConfigHelper.loadConfig(configurationFile, PluginConfiguration.class);
        this.messages = ConfigHelper.loadConfig(messagesFile, MessagesConfiguration.class);
    }

    public Logger getLogger() {
        return this.logger;
    }

    public PluginConfiguration getConfig() {
        return this.config;
    }

    public MessagesConfiguration getMessages() {
        return this.messages;
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    public UserManager getUserManager() {
        return this.userManager;
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
