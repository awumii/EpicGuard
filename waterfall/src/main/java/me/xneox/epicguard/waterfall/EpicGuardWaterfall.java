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
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.Platform;
import me.xneox.epicguard.waterfall.listener.DisconnectListener;
import me.xneox.epicguard.waterfall.listener.PlayerSettingsListener;
import me.xneox.epicguard.waterfall.listener.PostLoginListener;
import me.xneox.epicguard.waterfall.listener.PreLoginListener;
import me.xneox.epicguard.waterfall.listener.ServerPingListener;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class EpicGuardWaterfall extends Plugin implements Platform {
  private EpicGuard epicGuard;
  private BungeeAudiences adventure;

  @Override
  public void onEnable() {
    this.epicGuard = new EpicGuard(this);
    this.adventure = BungeeAudiences.create(this);

    var pluginManager = this.getProxy().getPluginManager();
    pluginManager.registerListener(this, new PreLoginListener(this.epicGuard));
    pluginManager.registerListener(this, new DisconnectListener(this.epicGuard));
    pluginManager.registerListener(this, new PostLoginListener(this.epicGuard));
    pluginManager.registerListener(this, new ServerPingListener(this.epicGuard));
    pluginManager.registerListener(this, new PlayerSettingsListener(this.epicGuard));

    pluginManager.registerCommand(this, new BungeeCommandHandler(this));
  }

  @Override
  public void onDisable() {
    this.epicGuard.shutdown();
  }

  @Override
  public @NotNull String platformVersion() {
    return ProxyServer.getInstance().getName() + "-" + ProxyServer.getInstance().getVersion();
  }

  @Override
  public @NotNull Logger logger() {
    return this.getSLF4JLogger();
  }

  @Override
  public Audience audience(@NotNull UUID uuid) {
    var player = ProxyServer.getInstance().getPlayer(uuid);
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

  @NotNull
  public BungeeAudiences adventure() {
    return this.adventure;
  }

  @NotNull
  public EpicGuard epicGuard() {
    return this.epicGuard;
  }
}
