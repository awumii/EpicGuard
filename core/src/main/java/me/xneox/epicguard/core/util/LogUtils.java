package me.xneox.epicguard.core.util;

import me.xneox.epicguard.core.EpicGuardAPI;
import me.xneox.epicguard.core.util.logging.LogWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * This util helps with various logging operations.
 */
public final class LogUtils {
  private static final LogWrapper LOGGER = EpicGuardAPI.INSTANCE.instance().logger();

  /**
   * Catches a Throwable and prints a detailed error message.
   *
   * @param throwable the caught exception
   * @param details details about where the error has occurred
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
