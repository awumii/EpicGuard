package me.xneox.epicguard.core.util;

public final class Logger {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("EpicGuard");

    public static void log(String msg) {
        LOGGER.info(msg);
    }

    public static void warn(String msg) {
        LOGGER.warning(msg);
    }

    public static void error(String msg) {
        LOGGER.severe(msg);
    }

    public static void error(String msg, Throwable cause) {
        error("");
        error(" An issue ocurred in EpicGuard!");
        error("  > Details: " + msg);
        error("  > Stacktrace: ");
        for (StackTraceElement element : cause.getStackTrace()) {
            error("   " + element.toString());
        }
        error(" Please report this issue on Discord or GitHub.");
        error("  > https://github.com/xxneox/EpicGuard/issues");
        error("");
    }

    private Logger() {}
}
