package me.ishift.epicguard.core.util;

import java.util.UUID;
import java.util.logging.Logger;

public interface MethodInterface {
    void sendActionBar(String message, UUID target);

    Logger getLogger();

    String getVersion();

    void scheduleSyncTask(Runnable task, long seconds);

    void scheduleAsyncTask(Runnable task, long seconds);
}
