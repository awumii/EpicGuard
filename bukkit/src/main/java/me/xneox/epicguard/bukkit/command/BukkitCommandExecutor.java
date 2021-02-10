package me.xneox.epicguard.bukkit.command;

import me.xneox.epicguard.core.command.CommandExecutor;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BukkitCommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {
    private final CommandExecutor command;

    public BukkitCommandExecutor(CommandExecutor command) {
        this.command = command;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.command.onCommand(args, new BukkitSender(sender));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return (List<String>) this.command.onTabComplete(args);
    }
}
