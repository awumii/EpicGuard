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
import me.xneox.epicguard.core.task.AttackResetTask;
import me.xneox.epicguard.core.task.DataSaveTask;
import me.xneox.epicguard.core.task.MonitorTask;
import me.xneox.epicguard.core.task.UpdateCheckerTask;
import me.xneox.epicguard.core.util.ConfigHelper;
import me.xneox.epicguard.core.logging.LogFilter;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class EpicGuard {
    private final StorageManager storageManager;
    private final GeoManager geoManager;
    private final UserManager userManager;
    private final CooldownManager cooldownManager;
    private final AttackManager attackManager;
    private final PlatformPlugin plugin;

    private PluginConfiguration config;
    private MessagesConfiguration messages;

    public EpicGuard(PlatformPlugin plugin) {
        this.plugin = plugin;

        getLogger().log("Loading configuration...");
        this.reloadConfig();

        this.storageManager = new StorageManager();
        this.attackManager = new AttackManager();
        this.userManager = new UserManager();
        this.cooldownManager = new CooldownManager();
        this.geoManager = new GeoManager(this.getLogger());

        try {
            Class.forName("org.apache.logging.log4j.core.filter.AbstractFilter");
            new LogFilter(this).register();
        } catch (ClassNotFoundException e) {
            getLogger().warning("LogFilter can't be enabled, because log4j is not found. If you want to use this feature, switch to Waterfall/Travertine.");
        }

        this.plugin.scheduleTask(new MonitorTask(this), 1L);
        this.plugin.scheduleTask(new AttackResetTask(this), 40L);
        this.plugin.scheduleTask(new UpdateCheckerTask(this), 1800L);
        this.plugin.scheduleTask(new DataSaveTask(this), TimeUnit.MINUTES.toSeconds(this.config.autoSaveInterval));

        EpicGuardAPI.setInstance(this);
        getLogger().log("EpicGuard v5 finished startup successfully.");
    }

    public void reloadConfig() {
        File dataFolder = new File("plugins/EpicGuard");
        dataFolder.mkdir();

        File configurationFile = new File(dataFolder, "config.yml");
        File messagesFile = new File(dataFolder, "messages.yml");
        this.config = ConfigHelper.loadConfig(configurationFile, PluginConfiguration.class);
        this.messages = ConfigHelper.loadConfig(messagesFile, MessagesConfiguration.class);
    }

    public void shutdown() {
        this.storageManager.save();
    }

    public GuardLogger getLogger() {
        return this.plugin.getGuardLogger();
    }

    public PlatformPlugin getPlugin() {
        return plugin;
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

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public AttackManager getAttackManager() {
        return attackManager;
    }
}
