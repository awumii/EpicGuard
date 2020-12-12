package me.xneox.epicguard.core.logging;

import java.util.logging.Logger;

/**
 * GuardLogger implementation for platforms using the {@link java.util.logging.Logger}
 */
public class LoggerJava implements GuardLogger {
    private final Logger logger;

    public LoggerJava(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(String message) {
        this.logger.info(message);
    }

    @Override
    public void warning(String message) {
        this.logger.warning(message);
    }
}
