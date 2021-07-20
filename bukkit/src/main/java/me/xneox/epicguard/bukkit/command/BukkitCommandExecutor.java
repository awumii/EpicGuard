package me.xneox.epicguard.bukkit.command;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BukkitCommandExecutor extends CommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {
    public BukkitCommandExecutor(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.handle(args, new BukkitCommandSender(sender));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return new ArrayList<>(this.onTabComplete(args));
    }
}
