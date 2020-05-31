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

package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.EpicGuardBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeNotify {
    public static void notify(String message) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (EpicGuardBungee.getInstance().getStatusPlayers().contains(player.getUniqueId())) {
                BungeeUtil.sendActionBar(player, message);
            }
        }
    }
}
