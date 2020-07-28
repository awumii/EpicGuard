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

import me.ishift.epicguard.bukkit.util.Reflections;
import me.ishift.epicguard.core.MethodInterface;
import me.ishift.epicguard.core.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.logging.Logger;

public class BukkitMethods implements MethodInterface {
    private final JavaPlugin plugin;

    public BukkitMethods(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void sendActionBar(String message, UUID target) {
        Reflections.sendActionBar(Bukkit.getPlayer(target), message);
    }

    @Override
    public String format(String message) {
        return ChatUtils.colored(message);
    }

    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }

    @Override
    public String getVersion() {
        return this.plugin.getDescription().getVersion();
    }

    @Override
    public void runTaskLater(Runnable task, long seconds) {
        Bukkit.getScheduler().runTaskLater(this.plugin, task, seconds * 20L);
    }

    @Override
    public void scheduleTask(Runnable task, long seconds) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, task, 20L, seconds * 20L);
    }
}
