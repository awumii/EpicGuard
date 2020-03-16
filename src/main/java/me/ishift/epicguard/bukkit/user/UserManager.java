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

package me.ishift.epicguard.bukkit.user;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private static final Map<UUID, User> userMap = new HashMap<>();

    /**
     * @param player Target player.
     * @return User object related to the player.
     */
    public static User getUser(Player player) {
        return userMap.get(player.getUniqueId());
    }

    /**
     * @param player Target player.
     */
    public static void addUser(Player player) {
        final User user = new User(player);
        userMap.put(player.getUniqueId(), user);
    }

    /**
     * @param player Target player.
     */
    public static void removeUser(Player player) {
        userMap.remove(player.getUniqueId());
    }
}
