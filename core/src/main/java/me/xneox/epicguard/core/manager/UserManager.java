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

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This manager will cache the {@link User} object's of the currently online players.
 */
public class UserManager {
    private final Map<UUID, User> userMap = new ConcurrentHashMap<>();

    public Collection<User> getUsers() {
        return this.userMap.values();
    }

    public User getOrCreate(UUID uuid) {
        return this.userMap.computeIfAbsent(uuid, User::new);
    }

    public void removeUser(UUID uuid) {
        this.userMap.remove(uuid);
    }
}
