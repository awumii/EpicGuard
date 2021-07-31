package me.xneox.epicguard.paper;

import me.xneox.epicguard.core.EpicGuard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BukkitCommandExecutor implements CommandExecutor, TabCompleter {
    private final EpicGuard epicGuard;

    public BukkitCommandExecutor(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.epicGuard.commandHandler().handleCommand(args, sender);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return new ArrayList<>(this.epicGuard.commandHandler().handleSuggestions(args));
    }
}
