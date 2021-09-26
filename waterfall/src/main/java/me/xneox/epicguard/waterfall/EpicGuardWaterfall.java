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

package me.xneox.epicguard.waterfall;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import me.xneox.epicguard.core.util.logging.LogWrapper;
import me.xneox.epicguard.core.util.logging.impl.SLF4JWrapper;
import me.xneox.epicguard.waterfall.listener.DisconnectListener;
import me.xneox.epicguard.waterfall.listener.PlayerSettingsListener;
import me.xneox.epicguard.waterfall.listener.PostLoginListener;
import me.xneox.epicguard.waterfall.listener.PreLoginListener;
import me.xneox.epicguard.waterfall.listener.ServerPingListener;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.Platform;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import org.bstats.bungeecord.Metrics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EpicGuardWaterfall extends Plugin implements Platform {
  private EpicGuard epicGuard;
  private LogWrapper logger;
  private BungeeAudiences adventure;

  @Override
  public void onEnable() {
    this.logger = new SLF4JWrapper(this.getSLF4JLogger());
    this.epicGuard = new EpicGuard(this);
    this.adventure = BungeeAudiences.create(this);

    PluginManager pm = this.getProxy().getPluginManager();
    pm.registerListener(this, new PreLoginListener(this.epicGuard));
    pm.registerListener(this, new DisconnectListener(this.epicGuard));
    pm.registerListener(this, new PostLoginListener(this.epicGuard));
    pm.registerListener(this, new ServerPingListener(this.epicGuard));
    pm.registerListener(this, new PlayerSettingsListener(this.epicGuard));

    pm.registerCommand(this, new BungeeCommandHandler(this));

    new Metrics(this, 5956);
  }

  @Override
  public void onDisable() {
    this.epicGuard.shutdown();
  }

  @Override
  @NotNull
  public String platformVersion() {
    return ProxyServer.getInstance().getName() + "-" + ProxyServer.getInstance().getVersion();
  }

  @Override
  @NotNull
  public LogWrapper logger() {
    return this.logger;
  }

  @Override
  @Nullable
  public Audience audience(@NotNull UUID uuid) {
    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
    if (player != null) {
      return this.adventure.player(player);
    }
    return null;
  }

  @Override
  public void disconnectUser(@NotNull UUID uuid, @NotNull Component component) {
    ProxyServer.getInstance()
        .getPlayer(uuid)
        .disconnect(BungeeUtils.toLegacyComponent(component));
  }

  @Override
  public void runTaskLater(@NotNull Runnable task, long seconds) {
    this.getProxy().getScheduler().schedule(this, task, seconds, TimeUnit.SECONDS);
  }

  @Override
  public void scheduleRepeatingTask(@NotNull Runnable task, long seconds) {
    this.getProxy().getScheduler().schedule(this, task, seconds, seconds, TimeUnit.SECONDS);
  }

  @Override
  public void disablePlugin() {
    // A very ugly way to forcefully crash the plugin to prevent it from further loading.
    // BungeeCord has no method to fully disable a plugin.
    throw new EpicGuardCrashedException();
  }

  public BungeeAudiences adventure() {
    return this.adventure;
  }

  public EpicGuard epicGuard() {
    return this.epicGuard;
  }
}
