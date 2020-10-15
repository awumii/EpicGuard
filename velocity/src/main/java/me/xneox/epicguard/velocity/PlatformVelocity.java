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

package me.xneox.epicguard.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.MessagePosition;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.PlatformPlugin;
import me.xneox.epicguard.core.util.ChatUtils;
import me.xneox.epicguard.velocity.command.EpicGuardCommand;
import me.xneox.epicguard.velocity.listener.DisconnectListener;
import me.xneox.epicguard.velocity.listener.PostLoginListener;
import me.xneox.epicguard.velocity.listener.PreLoginListener;
import me.xneox.epicguard.velocity.listener.ServerPingListener;
import net.kyori.text.TextComponent;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Plugin(id = "epicguard", name = "EpicGuard", version = "5.2.0")
public class PlatformVelocity implements PlatformPlugin {
    private final ProxyServer server;
    private final Logger logger;
    private EpicGuard epicGuard;

    @Inject
    public PlatformVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onEnable(ProxyInitializeEvent e) {
        this.epicGuard = new EpicGuard(this);
        this.server.getCommandManager().register(new EpicGuardCommand(this.epicGuard), "epicguard", "guard");

        EventManager manager = this.getServer().getEventManager();
        manager.register(this, new PostLoginListener(this.epicGuard));
        manager.register(this, new PreLoginListener(this.epicGuard));
        manager.register(this, new DisconnectListener(this.epicGuard));
        manager.register(this, new ServerPingListener(this.epicGuard));
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent e) {
        this.epicGuard.shutdown();
    }

    public ProxyServer getServer() {
        return this.server;
    }

    @Override
    public void sendActionBar(String message, UUID target) {
        Optional<Player> optional = this.getServer().getPlayer(target);
        optional.ifPresent(player -> player.sendMessage(TextComponent.of(ChatUtils.coloredLegacy(message)), MessagePosition.ACTION_BAR));
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    @Override
    public String getVersion() {
        Optional<PluginContainer> container = this.getServer().getPluginManager().fromInstance(this);
        if (container.isPresent()) {
            Optional<String> version = container.get().getDescription().getVersion();
            if (version.isPresent()) {
                return version.get();
            }
        }
        return null;
    }

    @Override
    public void runTaskLater(Runnable task, long seconds) {
        this.getServer().getScheduler()
                .buildTask(this, task)
                .delay(seconds, TimeUnit.SECONDS)
                .schedule();
    }

    @Override
    public void scheduleTask(Runnable task, long seconds) {
        this.getServer().getScheduler()
                .buildTask(this, task)
                .repeat(seconds, TimeUnit.SECONDS)
                .schedule();
    }
}
