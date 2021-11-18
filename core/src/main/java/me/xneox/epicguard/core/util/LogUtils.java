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

package me.xneox.epicguard.core.util;

import me.xneox.epicguard.core.EpicGuardAPI;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * This util helps with various logging operations.
 */
public final class LogUtils {
  private static final Logger LOGGER = EpicGuardAPI.INSTANCE.instance().logger();

  /**
   * Catches a Throwable and prints a detailed error message.
   *
   * @param details details about where the error has occurred
   * @param throwable the caught exception
   */
  public static void catchException(@NotNull String details, @NotNull Throwable throwable) {
    LOGGER.error("An error occurred in EpicGuard!");
    LOGGER.error("  > Details: " + details);
    LOGGER.error("  > Platform: " + EpicGuardAPI.INSTANCE.platformVersion());
    LOGGER.error("  > Stacktrace: ");
    LOGGER.error("", throwable);
  }

  /**
   * Logs a message if debug is enabled in the configuration.
   *
   * @param message message to be logged
   */
  public static void debug(@NotNull String message) {
    if (EpicGuardAPI.INSTANCE.instance().config().misc().debug()) {
      LOGGER.info("(Debug) " + message);
    }
  }
}
