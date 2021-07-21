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
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.proxy.ProxyServer;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.Platform;
import me.xneox.epicguard.core.logging.GuardLogger;
import me.xneox.epicguard.core.logging.impl.SLF4JLogger;
import me.xneox.epicguard.core.user.OnlineUser;
import me.xneox.epicguard.velocity.command.VelocityCommandExecutor;
import me.xneox.epicguard.velocity.listener.*;
import org.bstats.velocity.Metrics;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "epicguard",
        name = "EpicGuard",
        version = "{version}",
        description = "Bot protection system for Minecraft servers.",
        authors = "xNeox")
public class EpicGuardVelocity implements Platform {
    private final ProxyServer server;
    private final GuardLogger logger;
    private final Metrics.Factory metricsFactory;

    private EpicGuard epicGuard;

    @Inject
    public EpicGuardVelocity(ProxyServer server, Logger logger, Metrics.Factory metricsFactory) {
        this.server = server;
        this.logger = new SLF4JLogger(logger);
        this.metricsFactory = metricsFactory;
    }

    @Subscribe
    public void onEnable(ProxyInitializeEvent e) {
        this.epicGuard = new EpicGuard(this);

        CommandManager commandManager = this.server.getCommandManager();
        CommandMeta meta = commandManager.metaBuilder("epicguard")
                .aliases("guard")
                .build();

        commandManager.register(meta, new VelocityCommandExecutor(this.epicGuard));

        EventManager eventManager = this.server.getEventManager();
        eventManager.register(this, new PostLoginListener(this.epicGuard));
        eventManager.register(this, new PreLoginListener(this.epicGuard));
        eventManager.register(this, new DisconnectListener(this.epicGuard));
        eventManager.register(this, new ServerPingListener(this.epicGuard));
        eventManager.register(this, new PlayerSettingsListener(this.epicGuard));

        this.metricsFactory.make(this, 10417);
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent e) {
        this.epicGuard.shutdown();
    }

    @Override
    public @NotNull GuardLogger logger() {
        return this.logger;
    }

    @Override
    public void sendActionBar(@NotNull String message, @NotNull OnlineUser onlineUser) {
        this.server.getPlayer(onlineUser.uuid()).ifPresent(player -> player.sendActionBar(AdventureUtils.createComponent(message)));
    }

    @Override
    public void disconnectUser(@NotNull OnlineUser onlineUser, @NotNull String message) {
        this.server.getPlayer(onlineUser.uuid()).ifPresent(player -> player.disconnect(AdventureUtils.createComponent(message)));
    }

    @Override
    public String version() {
        Optional<PluginContainer> container = this.server.getPluginManager().fromInstance(this);
        if (container.isPresent()) {
            Optional<String> version = container.get().getDescription().getVersion();
            if (version.isPresent()) {
                return version.get();
            }
        }
        return null;
    }

    @Override
    public void runTaskLater(@NotNull Runnable task, long seconds) {
        this.server.getScheduler()
                .buildTask(this, task)
                .delay(seconds, TimeUnit.SECONDS)
                .schedule();
    }

    @Override
    public void scheduleRepeatingTask(@NotNull Runnable task, long seconds) {
        this.server.getScheduler()
                .buildTask(this, task)
                .repeat(seconds, TimeUnit.SECONDS)
                .schedule();
    }
}
