package me.ishift.epicguard.core.user;

import java.util.UUID;

public class User {
    private final UUID uuid;

    private boolean status;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public boolean isStatus() {
        return this.status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
