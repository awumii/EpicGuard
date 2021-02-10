package me.xneox.epicguard.core.command;

import java.util.UUID;

public abstract class Sender<C> {
    protected final C sender;

    protected Sender(C sender) {
        this.sender = sender;
    }

    public abstract void sendMessage(String message);

    public abstract boolean isPlayer();

    public abstract UUID getUUID();
}
