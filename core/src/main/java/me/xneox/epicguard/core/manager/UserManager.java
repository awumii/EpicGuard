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

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.xneox.epicguard.core.user.OnlineUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This manager caches the {@link OnlineUser} for currently online players
 */
public class UserManager {
  private final Map<UUID, OnlineUser> userMap = new ConcurrentHashMap<>();

  @NotNull
  public Collection<OnlineUser> users() {
    return this.userMap.values();
  }

  /**
   * Returns the {@link OnlineUser} for the specified
   * UUID. Creates a new object if absent.
   */
  @NotNull
  public OnlineUser getOrCreate(@NotNull UUID uuid) {
    return this.userMap.computeIfAbsent(uuid, OnlineUser::new);
  }

  /**
   * Returns the {@link OnlineUser} for the specified
   * UUID, or null if the user is offline.
   */
  @Nullable
  public OnlineUser get(@NotNull UUID uuid) {
    return this.userMap.get(uuid);
  }

  public void removeUser(@NotNull UUID uuid) {
    this.userMap.remove(uuid);
  }
}
