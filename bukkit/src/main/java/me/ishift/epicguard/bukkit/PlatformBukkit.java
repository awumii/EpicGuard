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

import me.ishift.epicguard.bukkit.command.EpicGuardCommand;
import me.ishift.epicguard.bukkit.listener.CommandListener;
import me.ishift.epicguard.bukkit.listener.PlayerJoinListener;
import me.ishift.epicguard.bukkit.listener.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.listener.PlayerQuitListener;
import me.ishift.epicguard.bukkit.listener.ServerPingListener;
import me.ishift.epicguard.bukkit.module.ModuleManager;
import me.ishift.epicguard.bukkit.module.ModuleTask;
import me.ishift.epicguard.bukkit.util.Metrics;
import me.ishift.epicguard.bukkit.util.Reflections;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.PlatformPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class PlatformBukkit extends JavaPlugin implements PlatformPlugin {
    private EpicGuard epicGuard;
    private ModuleManager moduleManager;

    @Override
    public void onEnable() {
        this.epicGuard = new EpicGuard(this);
        this.moduleManager = new ModuleManager(this.epicGuard);

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerPreLoginListener(this.epicGuard), this);
        pm.registerEvents(new PlayerQuitListener(this.epicGuard), this);
        pm.registerEvents(new PlayerJoinListener(this.epicGuard), this);
        pm.registerEvents(new ServerPingListener(this.epicGuard), this);
        pm.registerEvents(new CommandListener(this), this);

        Bukkit.getScheduler().runTaskTimer(this, new ModuleTask(this), 20L * 5L, 20L);
        this.getCommand("epicguard").setExecutor(new EpicGuardCommand(this, this.epicGuard));
        new Metrics(this, 5845);
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }

    public ModuleManager getModuleManager() {
        return this.moduleManager;
    }

    @Override
    public void sendActionBar(String message, UUID target) {
        Reflections.sendActionBar(Bukkit.getPlayer(target), message);
    }

    @Override
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public void runTaskLater(Runnable task, long seconds) {
        Bukkit.getScheduler().runTaskLater(this, task, seconds * 20L);
    }

    @Override
    public void scheduleTask(Runnable task, long seconds) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, task, 20L, seconds * 20L);
    }
}
