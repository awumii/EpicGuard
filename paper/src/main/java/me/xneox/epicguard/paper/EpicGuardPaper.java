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

package me.xneox.epicguard.paper;

import java.util.UUID;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.Platform;
import me.xneox.epicguard.paper.listener.PlayerPostLoginListener;
import me.xneox.epicguard.paper.listener.PlayerPreLoginListener;
import me.xneox.epicguard.paper.listener.PlayerQuitListener;
import me.xneox.epicguard.paper.listener.PlayerSettingsListener;
import me.xneox.epicguard.paper.listener.ServerPingListener;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class EpicGuardPaper extends JavaPlugin implements Platform {
  private EpicGuard epicGuard;

  @Override
  public void onEnable() {
    this.epicGuard = new EpicGuard(this);

    var pluginManager = Bukkit.getPluginManager();
    pluginManager.registerEvents(new PlayerPreLoginListener(this.epicGuard), this);
    pluginManager.registerEvents(new PlayerQuitListener(this.epicGuard), this);
    pluginManager.registerEvents(new PlayerPostLoginListener(this.epicGuard), this);
    pluginManager.registerEvents(new ServerPingListener(this.epicGuard), this);
    pluginManager.registerEvents(new PlayerSettingsListener(this.epicGuard), this);

    var command = this.getCommand("epicguard");
    if (command != null) {
      var handler = new PaperCommandHandler(this.epicGuard);
      command.setExecutor(handler);
      command.setTabCompleter(handler);
    }
  }

  @Override
  public void onDisable() {
    this.epicGuard.shutdown();
  }

  @Override
  public @NotNull String platformVersion() {
    return Bukkit.getVersion();
  }

  @Override
  public @NotNull Logger logger() {
    return this.getSLF4JLogger();
  }

  @Override
  public @Nullable Audience audience(@NotNull UUID uuid) {
    return Bukkit.getPlayer(uuid);
  }

  @Override
  public void disconnectUser(@NotNull UUID uuid, @NotNull Component message) {
    var player = Bukkit.getPlayer(uuid);
    if (player != null) {
      player.kick(message);
    }
  }

  @Override
  public void runTaskLater(@NotNull Runnable task, long seconds) {
    Bukkit.getScheduler().runTaskLater(this, task, seconds * 20L);
  }

  @Override
  public void scheduleRepeatingTask(@NotNull Runnable task, long seconds) {
    Bukkit.getScheduler().runTaskTimerAsynchronously(this, task, 20L, seconds * 20L);
  }
}
