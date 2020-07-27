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

package me.ishift.epicguard.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.velocity.command.EpicGuardCommand;
import me.ishift.epicguard.velocity.listener.DisconnectListener;
import me.ishift.epicguard.velocity.listener.PostLoginListener;
import me.ishift.epicguard.velocity.listener.PreLoginListener;
import me.ishift.epicguard.velocity.listener.ServerPingListener;

import java.util.logging.Logger;

@Plugin(id = "epicguard", name = "EpicGuard", version = "5.1.1")
public class EpicGuardVelocity {
    private final ProxyServer server;
    private final Logger logger;
    private EpicGuard epicGuard;

    @Inject
    public EpicGuardVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onEnable(ProxyInitializeEvent e) {
        this.epicGuard = new EpicGuard(new VelocityMethods(this));
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

    public Logger getLogger() {
        return this.logger;
    }

    public ProxyServer getServer() {
        return this.server;
    }
}
