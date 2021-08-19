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

package me.xneox.epicguard.core;

import java.util.UUID;
import me.xneox.epicguard.core.util.logging.LogWrapper;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Platform {
  @NotNull
  LogWrapper logger();

  String version();

  /** Returns an audience for the provided user. */
  @Nullable
  Audience audience(@NotNull UUID uuid);

  /**
   * Kicks the user from the server with a specified message (find the player using User#getUUID).
   *
   * @param uuid The UUID of user to be kicked.
   * @param message The kick message.
   */
  void disconnectUser(@NotNull UUID uuid, @NotNull Component message);

  /**
   * Schedules a task to be run asynchronously after the specified time (in seconds).
   *
   * @param task The task to be scheduled.
   * @param seconds Delay in seconds after the task should be ran.
   */
  void runTaskLater(@NotNull Runnable task, long seconds);

  /**
   * Schedules a task to be run asynchronously repeatedly with fixed delay (in seconds).
   *
   * @param task The task to be scheduled.
   * @param seconds Delay in seconds between each runs of the task.
   */
  void scheduleRepeatingTask(@NotNull Runnable task, long seconds);
}
