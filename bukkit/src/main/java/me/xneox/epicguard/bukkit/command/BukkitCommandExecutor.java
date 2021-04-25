package me.xneox.epicguard.bukkit.command;

import me.xneox.epicguard.core.command.CommandExecutor;
import org.bukkit.command.*;

import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class BukkitCommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {
    private final CommandExecutor command;

    public BukkitCommandExecutor(CommandExecutor command) {
        this.command = command;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        this.command.onCommand(args, new BukkitSender(sender));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        return (List<String>) this.command.onTabComplete(args);
    }
}
