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

import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.config.PluginConfiguration;
import me.xneox.epicguard.core.util.ExceptionUtils;
import me.xneox.epicguard.core.util.VersionUtils;
import me.xneox.epicguard.core.util.logging.LogFilter;
import me.xneox.epicguard.core.manager.AttackManager;
import me.xneox.epicguard.core.manager.GeoManager;
import me.xneox.epicguard.core.manager.UserManager;
import me.xneox.epicguard.core.proxy.ProxyManager;
import me.xneox.epicguard.core.storage.StorageManager;
import me.xneox.epicguard.core.task.AttackResetTask;
import me.xneox.epicguard.core.task.DataSaveTask;
import me.xneox.epicguard.core.task.MonitorTask;
import me.xneox.epicguard.core.task.UpdateCheckerTask;
import me.xneox.epicguard.core.util.ConfigurationLoader;
import me.xneox.epicguard.core.util.FileUtils;
import me.xneox.epicguard.core.util.logging.LogWrapper;
import org.spongepowered.configurate.ConfigurateException;

/** The main, core class of EpicGuard. */
public class EpicGuard {
  private final Platform platform;

  private StorageManager storageManager;
  private GeoManager geoManager;
  private UserManager userManager;
  private AttackManager attackManager;
  private ProxyManager proxyManager;

  private PluginConfiguration config;
  private MessagesConfiguration messages;

  public EpicGuard(Platform platform) {
    this.platform = platform;
    this.startup();
  }

  private void startup() {
    logger().info("Running on: " + this.platform.platformVersion());
    logger().info("Loading configuration...");
    this.loadConfigurations();

    logger().info("Initializing managers...");
    this.geoManager = new GeoManager(this);
    this.storageManager = new StorageManager(this);
    this.proxyManager = new ProxyManager(this);
    this.attackManager = new AttackManager();
    this.userManager = new UserManager();

    logger().info("Initializing LogFilter...");
    new LogFilter(this).register();

    logger().info("Scheduling tasks...");
    this.platform.scheduleRepeatingTask(new MonitorTask(this), 1L);
    this.platform.scheduleRepeatingTask(new UpdateCheckerTask(this), 1800L);
    this.platform.scheduleRepeatingTask(new AttackResetTask(this), this.config.misc().attackResetInterval());
    this.platform.scheduleRepeatingTask(new DataSaveTask(this), TimeUnit.MINUTES.toSeconds(this.config.misc().autoSaveInterval()));

    EpicGuardAPI.INSTANCE.instance(this);
    logger().info("Startup completed successfully. Welcome to EpicGuard v" + VersionUtils.VERSION);
  }

  public void loadConfigurations() {
    File configurationFile = new File(FileUtils.EPICGUARD_DIR, "settings.conf");
    File messagesFile = new File(FileUtils.EPICGUARD_DIR, "messages.conf");

    try {
      this.config = new ConfigurationLoader<>(configurationFile, PluginConfiguration.class).load();
      this.messages = new ConfigurationLoader<>(messagesFile, MessagesConfiguration.class).load();
    } catch (ConfigurateException exception) {
      ExceptionUtils.report("Couldn't load the configuration file", exception);
    }
  }

  public void shutdown() {
    try {
      this.storageManager.database().saveData();
    } catch (SQLException exception) {
      ExceptionUtils.report("Could not save data to the SQL database (during shutdown)", exception);
    }
  }

  public LogWrapper logger() {
    return this.platform.logger();
  }

  public Platform platform() {
    return this.platform;
  }

  public PluginConfiguration config() {
    return this.config;
  }

  public MessagesConfiguration messages() {
    return this.messages;
  }

  public UserManager userManager() {
    return this.userManager;
  }

  public GeoManager geoManager() {
    return this.geoManager;
  }

  public StorageManager storageManager() {
    return this.storageManager;
  }

  public AttackManager attackManager() {
    return this.attackManager;
  }

  public ProxyManager proxyManager() {
    return this.proxyManager;
  }
}
