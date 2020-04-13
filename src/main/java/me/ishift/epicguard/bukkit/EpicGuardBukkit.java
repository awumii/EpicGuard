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

import me.ishift.epicguard.bukkit.command.GuardCommand;
import me.ishift.epicguard.bukkit.command.GuardTabCompleter;
import me.ishift.epicguard.bukkit.listener.PlayerCommandListener;
import me.ishift.epicguard.bukkit.listener.PlayerInventoryClickListener;
import me.ishift.epicguard.bukkit.listener.PlayerJoinListener;
import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.listener.PlayerQuitListener;
import me.ishift.epicguard.bukkit.listener.ServerListPingListener;
import me.ishift.epicguard.bukkit.listener.TabCompletePacketListener;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.task.AttackToggleTask;
import me.ishift.epicguard.common.task.CounterResetTask;
import me.ishift.epicguard.common.util.Log4jFilter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class EpicGuardBukkit extends JavaPlugin {
    private static EpicGuardBukkit epicGuardBukkit;
    private UserManager userManager;

    @Override
    public void onEnable() {
        epicGuardBukkit = this;
        this.saveDefaultConfig();
        SpigotSettings.load();
        AttackManager.init();
        this.userManager = new UserManager();

        final PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(), this);
        pm.registerEvents(new ServerListPingListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PlayerQuitListener(), this);
        pm.registerEvents(new PlayerInventoryClickListener(), this);
        pm.registerEvents(new PlayerCommandListener(), this);

        final BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.runTaskTimerAsynchronously(this, new AttackToggleTask(), 0L, Configuration.checkConditionsDelay * 2L);
        scheduler.runTaskTimerAsynchronously(this, new CounterResetTask(), 0L, 20L);

        if (pm.isPluginEnabled("ProtocolLib")) {
            new TabCompletePacketListener(this);
        }

        if (Configuration.filterEnabled) {
            final Log4jFilter filter = new Log4jFilter();
            filter.setFilteredMessages(Configuration.filterValues);
            filter.registerFilter();
        }

        final PluginCommand command = this.getCommand("guard");
        if (command != null) {
            command.setExecutor(new GuardCommand());
            command.setTabCompleter(new GuardTabCompleter());
        }
    }

    public static EpicGuardBukkit getInstance() {
        return epicGuardBukkit;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
