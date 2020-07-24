package me.ishift.epicguard.core;

import me.ishift.epicguard.core.config.MessagesConfiguration;
import me.ishift.epicguard.core.config.PluginConfiguration;
import me.ishift.epicguard.core.manager.GeoManager;
import me.ishift.epicguard.core.manager.StorageManager;
import me.ishift.epicguard.core.manager.UserManager;
import me.ishift.epicguard.core.task.AttackResetTask;
import me.ishift.epicguard.core.task.CounterTask;
import me.ishift.epicguard.core.task.UpdateCheckerTask;
import me.ishift.epicguard.core.util.ConfigHelper;
import me.ishift.epicguard.core.util.LogFilter;
import me.ishift.epicguard.core.util.MethodInterface;

import java.io.File;
import java.util.logging.Logger;

public class EpicGuard {
    private final Logger logger;
    private final StorageManager storageManager;
    private final GeoManager geoManager;
    private final UserManager userManager;
    private final MethodInterface methodInterface;

    private PluginConfiguration config;
    private MessagesConfiguration messages;

    private boolean attack;
    private int connectionPerSecond;

    public EpicGuard(MethodInterface methodInterface) {
        this.methodInterface = methodInterface;
        this.logger = methodInterface.getLogger();
        logger.info("EpicGuard v5 is starting up...");

        this.reloadConfig();
        this.storageManager = new StorageManager();
        this.userManager = new UserManager();
        this.geoManager = new GeoManager(this);

        try {
            Class.forName("org.apache.logging.log4j.core.filter.AbstractFilter");
            new LogFilter(this.config.consoleFilter).register();
        } catch (ClassNotFoundException e) {
            logger.warning("LogFilter can't be enabled, because log4j is not found. If you want to use this feature, switch to Waterfall/Travertine.");
        }

        this.methodInterface.scheduleAsyncTask(new CounterTask(this), 1L);
        this.methodInterface.scheduleAsyncTask(new AttackResetTask(this), 30L);
        this.methodInterface.scheduleAsyncTask(new UpdateCheckerTask(this), 1800L);

        logger.info("EpicGuard v5 finished startup successfully.");
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

    public void shutdown() {
        this.storageManager.save();
    }

    public MethodInterface getMethodInterface() {
        return this.methodInterface;
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
