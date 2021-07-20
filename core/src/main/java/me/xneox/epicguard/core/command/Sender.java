package me.xneox.epicguard.core.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class Sender<C> {
    protected final C sender;

    protected Sender(C sender) {
        this.sender = sender;
    }

    public abstract void sendMessage(@NotNull String message);

    public abstract boolean isPlayer();

    @Nullable
    public abstract UUID uuid();
}
