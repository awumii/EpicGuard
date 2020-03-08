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

import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.api.GeoAPI;
import me.ishift.epicguard.bukkit.command.GuardCommand;
import me.ishift.epicguard.bukkit.command.GuardTabCompleter;
import me.ishift.epicguard.bukkit.listener.*;
import me.ishift.epicguard.bukkit.task.AttackTask;
import me.ishift.epicguard.bukkit.task.RefreshTask;
import me.ishift.epicguard.bukkit.task.UpdaterTask;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.bukkit.util.Metrics;
import me.ishift.epicguard.api.LogFilter;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.common.*;
import me.ishift.epicguard.api.GuardLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GuardBukkit extends JavaPlugin {

    /**
     * @return Instance of GuardBukkit.
     */
    public static GuardBukkit getInstance() {
        return JavaPlugin.getPlugin(GuardBukkit.class);
    }

    @Override
    public void onEnable() {
        EpicGuardAPI.setLogger(new GuardLogger("EpicGuard", "plugins/EpicGuard"));
        this.saveDefaultConfig();
        Config.loadBukkit();
        Messages.load();
        StorageManager.load();

        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerInventoryClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);
        if (pm.isPluginEnabled("ProtocolLib")) {
            new PlayerTabCompletePacket(this);
        }

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new RefreshTask(), 1L, 20L);
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new AttackTask(), 1L, 400L);
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new UpdaterTask(), 40L, 5800L);
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(this, new NotificationTask(NotificationTask.Server.SPIGOT), 20L, 1L);

        this.getCommand("epicguard").setExecutor(new GuardCommand());
        this.getCommand("epicguard").setTabCompleter(new GuardTabCompleter());

        if (Config.filterEnabled) {
            final LogFilter filter = new LogFilter();
            filter.setFilteredMessages(Config.filterValues);
            filter.registerFilter();
        }

        Updater.checkForUpdates();
        Bukkit.getOnlinePlayers().forEach(UserManager::addUser);

        final Metrics metrics = new Metrics(this, 5845);
        metrics.addCustomChart(new Metrics.SingleLineChart("stoppedBots", StorageManager::getBlockedBots));
        metrics.addCustomChart(new Metrics.SingleLineChart("checkedConnections", StorageManager::getCheckedConnections));

        EpicGuardAPI.setGeoApi(new GeoAPI(this.getDataFolder() + "/data"));
    }

    @Override
    public void onDisable() {
        StorageManager.save();
    }
}
