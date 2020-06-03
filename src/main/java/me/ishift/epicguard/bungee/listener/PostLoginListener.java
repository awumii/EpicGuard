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

package me.ishift.epicguard.bungee.listener;

import lombok.AllArgsConstructor;
import me.ishift.epicguard.bungee.EpicGuardBungee;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class PostLoginListener implements Listener {
    private final AttackManager manager;
    private final EpicGuardBungee plugin;

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();

        if (Configuration.autoWhitelist) {
            ProxyServer.getInstance().getScheduler().schedule(this.plugin, () -> {
                if (player.isConnected()) {
                    this.manager.getStorageManager().getStorage().whitelist(player.getAddress().getAddress().getHostAddress());
                }
            }, Configuration.autoWhitelistTime / 20, TimeUnit.SECONDS);
        }
    }
}
