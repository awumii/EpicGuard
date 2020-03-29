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

package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.common.util.EpicGuardAPI;
import me.ishift.epicguard.common.util.GeoAPI;
import me.ishift.epicguard.common.util.GuardLogger;
import me.ishift.epicguard.common.util.LogFilter;
import me.ishift.epicguard.bukkit.command.GuardCommand;
import me.ishift.epicguard.bukkit.command.GuardTabCompleter;
import me.ishift.epicguard.bukkit.listener.PlayerCommandListener;
import me.ishift.epicguard.bukkit.listener.PlayerInventoryClickListener;
import me.ishift.epicguard.bukkit.listener.PlayerJoinListener;
import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.listener.PlayerQuitListener;
import me.ishift.epicguard.bukkit.listener.PlayerTabCompletePacket;
import me.ishift.epicguard.bukkit.listener.ServerListPingListener;
import me.ishift.epicguard.bukkit.task.RefreshTask;
import me.ishift.epicguard.bukkit.task.UpdaterTask;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.bukkit.util.Metrics;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.Messages;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.task.AttackTask;
import me.ishift.epicguard.common.task.CounterTask;
import me.ishift.epicguard.common.task.NotificationTask;
import me.ishift.epicguard.common.util.DependencyLoader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicGuardBukkit extends JavaPlugin {

    /**
     * @return Instance of GuardBukkit.
     */
    public static EpicGuardBukkit getInstance() {
        return JavaPlugin.getPlugin(EpicGuardBukkit.class);
    }

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        Config.loadBukkit();
        Messages.load();
        DependencyLoader.checkJars();
        StorageManager.init();

        final PluginManager pm = this.getServer().getPluginManager();
        EpicGuardAPI.setLogger(new GuardLogger("EpicGuard", "plugins/EpicGuard"));

        pm.registerEvents(new PlayerPreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerInventoryClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);

        if (pm.isPluginEnabled("ProtocolLib")) {
            new PlayerTabCompletePacket(this);
        }

        Bukkit.getServer().getScheduler().runTaskTimer(this, new RefreshTask(), 1L, 20L);
        Bukkit.getServer().getScheduler().runTaskTimer(this, new AttackTask(), 1L, 900L);
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new UpdaterTask(), 80L, 5800L);
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new CounterTask(), 1L, 20L);
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new NotificationTask(NotificationTask.Server.SPIGOT), 20L, 5L);

        this.getCommand("epicguard").setExecutor(new GuardCommand());
        this.getCommand("epicguard").setTabCompleter(new GuardTabCompleter());

        if (Config.filterEnabled) {
            final LogFilter filter = new LogFilter();
            filter.setFilteredMessages(Config.filterValues);
            filter.registerFilter();
        }

        Updater.checkForUpdates();
        Bukkit.getOnlinePlayers().forEach(UserManager::addUser);

        new Metrics(this, 5845);

        EpicGuardAPI.setGeoApi(new GeoAPI("plugins/EpicGuard", Config.countryEnabled, Config.cityEnabled));
    }

    @Override
    public void onDisable() {
        StorageManager.getStorage().save();
    }
}