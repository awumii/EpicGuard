package me.ishift.epicguard.core.user;

import java.util.UUID;

public class User {
    private final UUID uuid;

    private boolean notifications;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public boolean isNotifications() {
        return this.notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }
}
