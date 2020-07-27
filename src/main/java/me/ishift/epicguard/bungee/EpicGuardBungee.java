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

package me.ishift.epicguard.bungee;

import me.ishift.epicguard.bungee.command.EpicGuardCommand;
import me.ishift.epicguard.bungee.listener.DisconnectListener;
import me.ishift.epicguard.bungee.listener.PostLoginListener;
import me.ishift.epicguard.bungee.listener.PreLoginListener;
import me.ishift.epicguard.bungee.listener.ServerPingListener;
import me.ishift.epicguard.bungee.util.Metrics;
import me.ishift.epicguard.core.EpicGuard;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class EpicGuardBungee extends Plugin {
    private EpicGuard epicGuard;

    @Override
    public void onEnable() {
        this.epicGuard = new EpicGuard(new BungeeMethods(this));

        PluginManager pm = this.getProxy().getPluginManager();
        pm.registerListener(this, new PreLoginListener(this.epicGuard));
        pm.registerListener(this, new DisconnectListener(this.epicGuard));
        pm.registerListener(this, new PostLoginListener(this.epicGuard));
        pm.registerListener(this, new ServerPingListener(this.epicGuard));

        pm.registerCommand(this, new EpicGuardCommand(this.epicGuard));

        new Metrics(this, 5956);
    }

    @Override
    public void onDisable() {
        this.epicGuard.shutdown();
    }
}
