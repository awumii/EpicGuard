package me.xneox.epicguard.bukkit;

import me.xneox.epicguard.core.command.CommandSubject;
import me.xneox.epicguard.core.command.EpicGuardCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BukkitCommandExecutor implements CommandExecutor, TabExecutor {
    private final EpicGuardCommand command;

    public BukkitCommandExecutor(EpicGuardCommand command) {
        this.command = command;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        CommandSubject subject = new CommandSubject();
        if (sender instanceof Player) {
            subject.setUUID(((Player) sender).getUniqueId());
        }

        this.command.onCommand(args, new CommandSubject());
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return (List<String>) this.command.onTabComplete(args);
    }
}
