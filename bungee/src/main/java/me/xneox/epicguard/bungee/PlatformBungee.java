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

package me.xneox.epicguard.bungee;

import me.xneox.epicguard.bungee.listener.DisconnectListener;
import me.xneox.epicguard.bungee.listener.PostLoginListener;
import me.xneox.epicguard.bungee.listener.PreLoginListener;
import me.xneox.epicguard.bungee.listener.ServerPingListener;
import me.xneox.epicguard.bungee.util.Metrics;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.PlatformPlugin;
import me.xneox.epicguard.core.command.CommandSubject;
import me.xneox.epicguard.core.command.EpicGuardCommand;
import me.xneox.epicguard.core.logging.GuardLogger;
import me.xneox.epicguard.core.logging.logger.JavaLogger;
import me.xneox.epicguard.core.logging.logger.SLF4JLogger;
import me.xneox.epicguard.core.user.User;
import me.xneox.epicguard.core.util.ChatUtils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class PlatformBungee extends Plugin implements PlatformPlugin {
    private EpicGuard epicGuard;
    private GuardLogger logger;

    @Override
    public void onEnable() {
        // Logging support for both BungeeCord and Waterfall(+forks).
        try {
            Class.forName("org.slf4j.Logger");
            this.logger = new SLF4JLogger(this.getSLF4JLogger());
        } catch (ClassNotFoundException e) {
            this.logger = new JavaLogger(this.getLogger());
        }

        this.epicGuard = new EpicGuard(this);

        PluginManager pm = this.getProxy().getPluginManager();
        pm.registerListener(this, new PreLoginListener(this.epicGuard));
        pm.registerListener(this, new DisconnectListener(this.epicGuard));
        pm.registerListener(this, new PostLoginListener(this.epicGuard));
        pm.registerListener(this, new ServerPingListener(this.epicGuard));
        pm.registerCommand(this, new BungeeCommandExecutor(new EpicGuardCommand(this.epicGuard)));

        new Metrics(this, 5956);
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }

    @Override
    public GuardLogger getGuardLogger() {
        return this.logger;
    }

    @Override
    public void sendActionBar(@Nonnull String message, @Nonnull User user) {
        ProxyServer.getInstance().getPlayer(user.getUUID()).sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtils.colored(message)));
    }

    @Override
    public void sendMessage(@Nonnull String message, @Nonnull CommandSubject user) {
        if (user.isConsole()) {
            ProxyServer.getInstance().getConsole().sendMessage(new TextComponent(message));
        } else {
            ProxyServer.getInstance().getPlayer(user.getUUID()).sendMessage(new TextComponent(message));
        }
    }

    @Override
    public String getVersion() {
        return this.getDescription().getVersion();
    }

    @Override
    public void runTaskLater(@Nonnull Runnable task, long seconds) {
        this.getProxy().getScheduler().schedule(this, task, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void scheduleTask(@Nonnull Runnable task, long seconds) {
        this.getProxy().getScheduler().schedule(this, task, seconds, seconds, TimeUnit.SECONDS);
    }
}
