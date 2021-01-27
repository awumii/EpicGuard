package me.xneox.epicguard.core.command;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Holds UUID of the player who executed the /epicguard command.
 * UUID can be null if command is executed from the console.
 *
 * NOTE: Temporary solution for the multi-platform command system...
 */
public class CommandSubject {
    private UUID uuid;

    public boolean isConsole() {
        return this.uuid == null;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    @Nullable
    public UUID getUUID() {
        return this.uuid;
    }
}
