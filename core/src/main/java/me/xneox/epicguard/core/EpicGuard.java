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
import me.xneox.epicguard.core.proxy.ProxyService;
import me.xneox.epicguard.core.proxy.ProxyServiceSerializer;
import me.xneox.epicguard.core.util.LogUtils;
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
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

/**
 * The main class of the EpicGuard's core.
 * Initializes everything and holds all managers.
 */
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
    EpicGuardAPI.INSTANCE.instance(this);

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

    logger().info("Startup completed successfully. Welcome to EpicGuard v" + VersionUtils.CURRENT_VERSION);
  }

  public void loadConfigurations() {
    var configLoader = HoconConfigurationLoader.builder()
        .defaultOptions(opt -> opt.serializers(builder -> builder.register(ProxyService.class, ProxyServiceSerializer.INSTANCE)))
        .file(new File(FileUtils.EPICGUARD_DIR, "settings.conf"))
        .build();

    var messagesLoader = HoconConfigurationLoader.builder()
        .file(new File(FileUtils.EPICGUARD_DIR, "messages.conf"))
        .build();

    try {
      this.config = new ConfigurationLoader<>(PluginConfiguration.class, configLoader).load();
      this.messages = new ConfigurationLoader<>(MessagesConfiguration.class, messagesLoader).load();
    } catch (ConfigurateException exception) {
      LogUtils.catchException("Couldn't load the configuration file", exception);
    }
  }

  public void shutdown() {
    try {
      this.storageManager.database().save();
    } catch (SQLException exception) {
      LogUtils.catchException("Could not save data to the SQL database (during shutdown)", exception);
    }
  }

  @NotNull
  public Logger logger() {
    return this.platform.logger();
  }

  @NotNull
  public Platform platform() {
    return this.platform;
  }

  @NotNull
  public PluginConfiguration config() {
    return this.config;
  }

  @NotNull
  public MessagesConfiguration messages() {
    return this.messages;
  }

  @NotNull
  public UserManager userManager() {
    return this.userManager;
  }

  @NotNull
  public GeoManager geoManager() {
    return this.geoManager;
  }

  @NotNull
  public StorageManager storageManager() {
    return this.storageManager;
  }

  @NotNull
  public AttackManager attackManager() {
    return this.attackManager;
  }

  @NotNull
  public ProxyManager proxyManager() {
    return this.proxyManager;
  }
}
