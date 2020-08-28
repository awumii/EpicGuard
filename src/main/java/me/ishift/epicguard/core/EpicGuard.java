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

package me.ishift.epicguard.core;

import me.ishift.epicguard.core.config.MessagesConfiguration;
import me.ishift.epicguard.core.config.PluginConfiguration;
import me.ishift.epicguard.core.manager.CooldownManager;
import me.ishift.epicguard.core.manager.GeoManager;
import me.ishift.epicguard.core.manager.StorageManager;
import me.ishift.epicguard.core.manager.UserManager;
import me.ishift.epicguard.core.task.AttackResetTask;
import me.ishift.epicguard.core.task.MonitorTask;
import me.ishift.epicguard.core.task.UpdateCheckerTask;
import me.ishift.epicguard.core.util.ConfigHelper;
import me.ishift.epicguard.core.util.LogFilter;

import java.io.File;
import java.util.logging.Logger;

public class EpicGuard {
    private final Logger logger;
    private final StorageManager storageManager;
    private final GeoManager geoManager;
    private final UserManager userManager;
    private final CooldownManager cooldownManager;
    private final PlatformPlugin plugin;

    private PluginConfiguration config;
    private MessagesConfiguration messages;

    private boolean attack;
    private int connectionPerSecond;

    public EpicGuard(PlatformPlugin plugin) {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
        logger.info("EpicGuard v5 is starting up...");

        this.reloadConfig();
        this.storageManager = new StorageManager();
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
        this.storageManager.save();
    }

    public PlatformPlugin getPlugin() {
        return this.plugin;
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

    public CooldownManager getCooldownManager() {
        return this.cooldownManager;
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

    public void resetConnections() {
        this.connectionPerSecond = 0;
    }

    public void addConnectionPerSecond() {
        this.connectionPerSecond++;
    }
}
