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

import java.util.concurrent.TimeUnit;
import me.xneox.epicguard.waterfall.listener.DisconnectListener;
import me.xneox.epicguard.waterfall.listener.PlayerSettingsListener;
import me.xneox.epicguard.waterfall.listener.PostLoginListener;
import me.xneox.epicguard.waterfall.listener.PreLoginListener;
import me.xneox.epicguard.waterfall.listener.ServerPingListener;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.Platform;
import me.xneox.epicguard.core.user.OnlineUser;
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
import org.slf4j.Logger;

public class EpicGuardWaterfall extends Plugin implements Platform {
  private EpicGuard epicGuard;

  private BungeeAudiences adventure;

  @Override
  public void onEnable() {
    this.epicGuard = new EpicGuard(this);
    this.adventure = BungeeAudiences.create(this);

    PluginManager pm = this.getProxy().getPluginManager();
    pm.registerListener(this, new PreLoginListener(this.epicGuard));
    pm.registerListener(this, new DisconnectListener(this.epicGuard));
    pm.registerListener(this, new PostLoginListener(this.epicGuard));
    pm.registerListener(this, new ServerPingListener(this.epicGuard));
    pm.registerListener(this, new PlayerSettingsListener(this.epicGuard));

    pm.registerCommand(this, new BungeeCommandExecutor(this));

    new Metrics(this, 5956);
  }

  @Override
  public void onDisable() {
    this.epicGuard.shutdown();
  }

  @Override
  @NotNull
  public Logger logger() {
    return this.getSLF4JLogger();
  }

  @Override
  public String version() {
    return this.getDescription().getVersion();
  }

  @Override
  @Nullable
  public Audience audience(@NotNull OnlineUser user) {
    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(user.uuid());
    if (player != null) {
      return this.adventure.player(player);
    }
    return null;
  }

  @Override
  public void disconnectUser(@NotNull OnlineUser onlineUser, @NotNull Component component) {
    ProxyServer.getInstance()
        .getPlayer(onlineUser.uuid())
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

  public BungeeAudiences adventure() {
    return this.adventure;
  }

  public EpicGuard epicGuard() {
    return this.epicGuard;
  }
}
