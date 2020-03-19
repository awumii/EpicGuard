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
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.Messages;
import me.ishift.epicguard.common.StorageManager;

@Plugin(id = "EpicGuard", name = "EpicGuard", version = "1.0-SNAPSHOT",
        description = "Advanced server protection.", authors = {"iShift", "ruzekh"})
public class EpicGuardVelocity {
    private ProxyServer server;

    @Inject
    public EpicGuardVelocity(ProxyServer server) {
        this.server = server;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        StorageManager.load();
        Config.loadBungee();
        Messages.load();
        server.getEventManager().register(this, new PreLoginListener());
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        StorageManager.save();
    }
}
