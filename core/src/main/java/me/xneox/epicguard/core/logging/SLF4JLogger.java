package me.xneox.epicguard.core.logging;

import org.slf4j.Logger;

/**
 * GuardLogger implementation for platforms using the {@link org.slf4j.Logger}
 */
public class SLF4JLogger implements GuardLogger {
    private final Logger logger;

    public SLF4JLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void log(String message) {
        this.logger.info(message);
    }

    @Override
    public void warning(String message) {
        this.logger.warn(message);
    }
}
