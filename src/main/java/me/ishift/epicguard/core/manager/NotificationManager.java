package me.ishift.epicguard.core.manager;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.user.User;
import me.ishift.epicguard.core.util.MethodInterface;

public class NotificationManager {
    private final EpicGuard epicGuard;

    public NotificationManager(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public void notify(String message) {
        for (User user : this.epicGuard.getUserManager().getUsers()) {
            if (user.isNotifications()) {
                this.epicGuard.getMethodInterface().sendActionBar(message, user.getUniqueId());
            }
        }
    }
}
