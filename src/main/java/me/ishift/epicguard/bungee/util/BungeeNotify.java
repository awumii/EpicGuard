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

import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BungeeNotify {
    @Getter private static final List<UUID> users = new ArrayList<>();

    public static void notify(String message) {
        ProxyServer.getInstance().getPlayers().stream()
                .filter(player -> users.contains(player.getUniqueId()))
                .forEach(player -> BungeeUtil.sendActionBar(player, message));
    }
}
