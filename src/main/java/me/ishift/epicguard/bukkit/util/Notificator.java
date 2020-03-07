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

package me.ishift.epicguard.bukkit.util;

import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.api.bukkit.ActionBarAPI;
import me.ishift.epicguard.common.Messages;
import me.ishift.epicguard.api.ChatUtil;
import org.bukkit.Bukkit;

public class Notificator {
    public static void broadcast(String text) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final User u = UserManager.getUser(player);
            if (u != null && player.hasPermission("epicguard.admin")) {
                player.sendMessage(ChatUtil.fix(Messages.prefix + text));
            }
        });
    }

    public static void action(String text) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final User u = UserManager.getUser(player);
            if (u != null && u.isNotifications()) {
                if (player.hasPermission("epicguard.admin")) {
                    ActionBarAPI.sendActionBar(player, text);
                }
            }
        });
    }
}
