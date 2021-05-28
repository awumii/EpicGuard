package me.xneox.epicguard.core.logging.impl;

import me.xneox.epicguard.core.logging.GuardLogger;
import org.slf4j.Logger;

/**
 * GuardLogger implementation and a wrapper around the {@link org.slf4j.Logger}
 */
public class SLF4JLogger implements GuardLogger {
    private final Logger logger;

    public SLF4JLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void warning(String message) {
        this.logger.warn(message);
    }

    @Override
    public void error(String message) {
        this.logger.error(message);
    }
}
