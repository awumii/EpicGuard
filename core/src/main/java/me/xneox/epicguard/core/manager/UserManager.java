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

package me.xneox.epicguard.core.manager;

import me.xneox.epicguard.core.user.User;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {
    private final Map<UUID, User> userMap = new HashMap<>();

    public Collection<User> getUsers() {
        return this.userMap.values();
    }

    public User getUser(UUID uuid) {
        return this.userMap.get(uuid);
    }

    public void addUser(UUID uuid) {
        this.userMap.put(uuid, new User(uuid));
    }

    public void removeUser(UUID uuid) {
        this.userMap.remove(uuid);
    }
}
