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

import me.ishift.epicguard.common.AttackManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final Map<UUID, User> userMap;
    private final AttackManager manager;

    public UserManager(AttackManager manager) {
        this.userMap = new HashMap<>();
        this.manager = manager;
    }

    public User getUser(Player player) {
        return this.userMap.get(player.getUniqueId());
    }

    public void createUser(Player player) {
        this.userMap.put(player.getUniqueId(), new User(player, this.manager));
    }

    public void removeUser(Player player) {
        final User user = this.getUser(player);
        user.save();
        this.userMap.remove(player.getUniqueId());
    }
}
