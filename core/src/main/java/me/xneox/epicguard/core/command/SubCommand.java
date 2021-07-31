package me.xneox.epicguard.core.command;

import me.xneox.epicguard.core.EpicGuard;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public interface SubCommand {
    void execute(@NotNull Audience audience, @NotNull String[] args, @NotNull EpicGuard epicGuard);

    default @NotNull Collection<String> suggest(@NotNull String[] args, @NotNull EpicGuard epicGuard) {
        return new ArrayList<>();
    }
}
