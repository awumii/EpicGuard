package me.ishift.epicguard.core.util;

import java.util.UUID;

public interface Notificator {
    void sendActionBar(String message, UUID target);
}
