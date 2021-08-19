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
import me.xneox.epicguard.core.util.logging.LogWrapper;
import me.xneox.epicguard.core.util.logging.impl.JavaLoggerWrapper;
import me.xneox.epicguard.paper.listener.PlayerJoinListener;
import me.xneox.epicguard.paper.listener.PlayerPreLoginListener;
import me.xneox.epicguard.paper.listener.PlayerQuitListener;
import me.xneox.epicguard.paper.listener.PlayerSettingsListener;
import me.xneox.epicguard.paper.listener.ServerPingListener;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EpicGuardPaper extends JavaPlugin implements Platform {
  private EpicGuard epicGuard;
  private LogWrapper logger;

  @Override
  public void onEnable() {
    this.logger = new JavaLoggerWrapper(this.getLogger());
    this.epicGuard = new EpicGuard(this);

    PluginManager pm = Bukkit.getPluginManager();
    pm.registerEvents(new PlayerPreLoginListener(this.epicGuard), this);
    pm.registerEvents(new PlayerQuitListener(this.epicGuard), this);
    pm.registerEvents(new PlayerJoinListener(this.epicGuard), this);
    pm.registerEvents(new ServerPingListener(this.epicGuard), this);
    pm.registerEvents(new PlayerSettingsListener(this.epicGuard), this);

    PluginCommand command = this.getCommand("epicguard");
    if (command != null) {
      PaperCommandExecutor cmdExecutor = new PaperCommandExecutor(this.epicGuard);
      command.setExecutor(cmdExecutor);
      command.setTabCompleter(cmdExecutor);
    }

    new Metrics(this, 5845);
  }

  @Override
  public void onDisable() {
    this.epicGuard.shutdown();
  }

  @Override
  @NotNull
  public LogWrapper logger() {
    return this.logger;
  }

  @Override
  public String version() {
    return this.getDescription().getVersion();
  }

  @Override
  @Nullable
  public Audience audience(@NotNull UUID uuid) {
    return Bukkit.getPlayer(uuid);
  }

  @Override
  public void disconnectUser(@NotNull UUID uuid, @NotNull Component message) {
    Player player = Bukkit.getPlayer(uuid);
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
