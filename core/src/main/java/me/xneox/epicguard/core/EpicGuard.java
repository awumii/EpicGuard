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
import me.xneox.epicguard.core.manager.AttackManager;
import me.xneox.epicguard.core.manager.CooldownManager;
import me.xneox.epicguard.core.manager.GeoManager;
import me.xneox.epicguard.core.storage.StorageFactory;
import me.xneox.epicguard.core.storage.StorageSystem;
import me.xneox.epicguard.core.manager.UserManager;
import me.xneox.epicguard.core.task.AttackResetTask;
import me.xneox.epicguard.core.task.MonitorTask;
import me.xneox.epicguard.core.task.UpdateCheckerTask;
import me.xneox.epicguard.core.util.ConfigHelper;
import me.xneox.epicguard.core.util.LogFilter;

import java.io.File;
import java.util.logging.Logger;

public class EpicGuard {
    private final Logger logger;
    private final StorageSystem storageSystem;
    private final GeoManager geoManager;
    private final UserManager userManager;
    private final CooldownManager cooldownManager;
    private final AttackManager attackManager;
    private final PlatformPlugin plugin;

    private PluginConfiguration config;
    private MessagesConfiguration messages;

    public EpicGuard(PlatformPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        this.reloadConfig();

        StorageFactory storageFactory = new StorageFactory(this);
        this.storageSystem = storageFactory.createStorage(this.config.storageType);
        this.storageSystem.load();

        this.attackManager = new AttackManager();
        this.userManager = new UserManager();
        this.cooldownManager = new CooldownManager();
        this.geoManager = new GeoManager(this);

        try {
            Class.forName("org.apache.logging.log4j.core.filter.AbstractFilter");
            new LogFilter(this.config.consoleFilter).register();
        } catch (ClassNotFoundException e) {
            logger.warning("LogFilter can't be enabled, because log4j is not found. If you want to use this feature, switch to Waterfall/Travertine.");
        }

        this.plugin.scheduleTask(new MonitorTask(this), 1L);
        this.plugin.scheduleTask(new AttackResetTask(this), 40L);
        this.plugin.scheduleTask(new UpdateCheckerTask(this), 1800L);

        logger.info("EpicGuard v5 finished startup successfully.");
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
        this.storageSystem.save();
    }

    public PlatformPlugin getPlugin() {
        return plugin;
    }

    public Logger getLogger() {
        return logger;
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

    public StorageSystem getStorageManager() {
        return storageSystem;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public AttackManager getAttackManager() {
        return attackManager;
    }
}
