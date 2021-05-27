/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.core;

import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.config.PluginConfiguration;
import me.xneox.epicguard.core.logging.GuardLogger;
import me.xneox.epicguard.core.manager.*;
import me.xneox.epicguard.core.storage.StorageManager;
import me.xneox.epicguard.core.task.AttackResetTask;
import me.xneox.epicguard.core.task.DataSaveTask;
import me.xneox.epicguard.core.task.MonitorTask;
import me.xneox.epicguard.core.task.UpdateCheckerTask;
import me.xneox.epicguard.core.logging.LogFilter;
import me.xneox.epicguard.core.util.ConfigUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class EpicGuard {
    private final StorageManager storageManager;
    private final GeoManager geoManager;
    private final UserManager userManager;
    private final AttackManager attackManager;

    private PluginConfiguration config;
    private MessagesConfiguration messages;

    private final Platform platform;

    public EpicGuard(Platform platform) {
        this.platform = platform;

        getLogger().info("Loading configuration...");
        this.loadConfigurations();

        getLogger().info("Initializing managers...");
        this.storageManager = new StorageManager();
        this.attackManager = new AttackManager();
        this.userManager = new UserManager();
        this.geoManager = new GeoManager(this.getLogger());

        getLogger().info("Initializing LogFilter...");
        try {
            Class.forName("org.apache.logging.log4j.core.filter.AbstractFilter");
            new LogFilter(this).register();
        } catch (ClassNotFoundException e) {
            getLogger().warning("LogFilter can't be enabled, because log4j is not found.");
            getLogger().warning("If you want to use this feature, switch to Waterfall/Travertine."); // This can only occur on bungeecord.
        }

        getLogger().info("Scheduling tasks...");
        this.platform.scheduleTask(new MonitorTask(this), 1L);
        this.platform.scheduleTask(new UpdateCheckerTask(this), 1800L);
        this.platform.scheduleTask(new AttackResetTask(this), this.config.attackResetInterval());
        this.platform.scheduleTask(new DataSaveTask(this), TimeUnit.MINUTES.toSeconds(this.config.autoSaveInterval()));

        EpicGuardAPI.setInstance(this);
        getLogger().info("Startup completed successfully. Welcome to EpicGuard v" + this.platform.getVersion());
    }

    /**
     * Loads the configuration and messages.
     */
    public void loadConfigurations() {
        File dataFolder = new File("plugins/EpicGuard");
        dataFolder.mkdir();

        File configurationFile = new File(dataFolder, "settings.conf");
        File messagesFile = new File(dataFolder, "messages.conf");

        this.config = ConfigUtils.loadConfig(configurationFile, PluginConfiguration.class);
        this.messages = ConfigUtils.loadConfig(messagesFile, MessagesConfiguration.class);
    }

    /**
     * Safely shut down the plugin, saving the data.
     */
    public void shutdown() {
        this.storageManager.getProvider().save();
    }

    public GuardLogger getLogger() {
        return this.platform.getGuardLogger();
    }

    public Platform getPlatform() {
        return platform;
    }

    public PluginConfiguration getConfig() {
        return config;
    }

    public MessagesConfiguration getMessages() {
        return messages;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public GeoManager getGeoManager() {
        return geoManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public AttackManager getAttackManager() {
        return attackManager;
    }
}
