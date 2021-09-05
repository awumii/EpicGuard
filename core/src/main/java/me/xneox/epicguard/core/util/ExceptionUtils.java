package me.xneox.epicguard.core.util;

import me.xneox.epicguard.core.EpicGuardAPI;
import me.xneox.epicguard.core.util.logging.LogWrapper;
import org.jetbrains.annotations.NotNull;

public final class ExceptionUtils {
  public static void report(@NotNull String details, @NotNull Throwable throwable) {
    LogWrapper logger = EpicGuardAPI.INSTANCE.instance().logger();

    logger.error("An error occurred in EpicGuard!");
    logger.error("  > Details: " + details);
    logger.error("  > Platform: " + EpicGuardAPI.INSTANCE.platformVersion());
    logger.error("  > Stacktrace: ");
    throwable.printStackTrace();
  }
}
