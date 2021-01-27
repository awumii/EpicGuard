package me.xneox.epicguard.core.logging;

/**
 * Used as a wrapper around a logger provided by the platform EpicGuard is running on.
 */
public interface GuardLogger {
    void log(String message);

    void warning(String message);
}
