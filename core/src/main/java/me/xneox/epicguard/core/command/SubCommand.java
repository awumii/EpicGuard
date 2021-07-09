package me.xneox.epicguard.core.command;

import me.xneox.epicguard.core.EpicGuard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface SubCommand {
    void execute(@NotNull Sender<?> sender, @NotNull String[] args, @NotNull EpicGuard epicGuard);

    default @Nullable Collection<String> suggest(@NotNull String[] args, @NotNull EpicGuard epicGuard) {
        return null;
    }
}
