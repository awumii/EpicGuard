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

import me.xneox.epicguard.core.logging.GuardLogger;
import me.xneox.epicguard.core.user.User;

import javax.annotation.Nonnull;

/**
 * This is the most important part of the EpicGuard's platform implementation.
 * The main class of every platform has to implement this interface.
 *
 * The first thing the platforms has to do is to initialize the logger.
 */
public interface Platform {
    /**
     * There are already two implementations of this logger, meant to be used as a wrapper around
     * your platform's logging system.
     * {@link me.xneox.epicguard.core.logging.impl.SLF4JLogger} and {@link me.xneox.epicguard.core.logging.impl.JavaLogger}
     *
     * @return An implementation of the {@link GuardLogger} compatible with your platform.
     */
    @Nonnull
    GuardLogger logger();

    /**
     * @return Version of the plugin.
     */
    String version();

    /**
     * Send an action bar message to the specified user (find the player using User#getUUID).
     *
     * @param message Message to be sent.
     * @param user The user the message has to be sent to.
     */
    void sendActionBar(@Nonnull String message, @Nonnull User user);

    /**
     * Kicks the user from the server with a specified message (find the player using User#getUUID).
     *
     * @param user The user to be kicked.
     * @param message The kick message.
     */
    void disconnectUser(@Nonnull User user, @Nonnull String message);

    /**
     * Schedules a task to be run asynchronously after the specified time (in seconds).
     *
     * @param task The task to be scheduled.
     * @param seconds Delay in seconds after the task should be ran.
     */
    void runTaskLater(@Nonnull Runnable task, long seconds);

    /**
     * Schedules a task to be run asynchronously repeatedly with fixed delay (in seconds).
     *
     * @param task The task to be scheduled.
     * @param seconds Delay in seconds between each runs of the task.
     */
    void runTaskRepeating(@Nonnull Runnable task, long seconds);
}
