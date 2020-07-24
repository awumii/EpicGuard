package me.ishift.epicguard.core;

import java.util.UUID;
import java.util.logging.Logger;

public interface MethodInterface {
    void sendActionBar(String message, UUID target);

    Logger getLogger();

    String getVersion();

    void runTaskLater(Runnable task, long seconds);

    void scheduleTask(Runnable task, long seconds);
}
