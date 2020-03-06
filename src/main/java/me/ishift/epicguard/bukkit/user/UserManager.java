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

public class UserManager {
    private static final Map<Player, User> userMap = new HashMap<>();

    public static User getUser(Player p) {
        return userMap.get(p);
    }

    public static void addUser(Player p) {
        User user = new User(p);
        userMap.put(p, user);
    }

    public static void removeUser(Player p) {
        userMap.remove(p);
    }
}
