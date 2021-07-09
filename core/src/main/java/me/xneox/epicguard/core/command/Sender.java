package me.xneox.epicguard.core.command;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public abstract class Sender<C> {
    protected final C sender;

    protected Sender(C sender) {
        this.sender = sender;
    }

    public abstract void sendMessage(@Nonnull String message);

    public abstract boolean isPlayer();

    @Nullable
    public abstract UUID uuid();
}
