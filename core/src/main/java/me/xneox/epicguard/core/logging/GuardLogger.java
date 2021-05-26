package me.xneox.epicguard.core.logging;

/**
 * Used as a wrapper around a logger provided by the platform EpicGuard is running on.
 * Available implementations:
 *  {@link me.xneox.epicguard.core.logging.impl.JavaLogger} for {@link java.util.logging.Logger}
 *  {@link me.xneox.epicguard.core.logging.impl.SLF4JLogger} for {@link org.slf4j.Logger}
 */
public interface GuardLogger {
    void info(String message);

    void warning(String message);
}
