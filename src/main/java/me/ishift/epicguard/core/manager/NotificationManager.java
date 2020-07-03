package me.ishift.epicguard.core.manager;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.user.User;
import me.ishift.epicguard.core.util.Notificator;

public class NotificationManager {
    private final EpicGuard epicGuard;
    private final Notificator notificator;

    public NotificationManager(EpicGuard epicGuard, Notificator notificator) {
        this.epicGuard = epicGuard;
        this.notificator = notificator;
    }

    public void notify(String message) {
        for (User user : this.epicGuard.getUserManager().getUsers()) {
            if (user.isNotifications()) {
                this.notificator.sendActionBar(message, user.getUniqueId());
            }
        }
    }
}
