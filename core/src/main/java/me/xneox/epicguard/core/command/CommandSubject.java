package me.xneox.epicguard.core.command;

import java.util.UUID;

public class CommandSubject {
    private UUID uuid;

    public boolean isConsole() {
        return this.uuid != null;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }
}
