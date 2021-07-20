package me.xneox.epicguard.bukkit.command;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.CommandExecutor;
import org.bukkit.command.*;

import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BukkitCommandExecutor extends CommandExecutor implements org.bukkit.command.CommandExecutor, TabCompleter {
    public BukkitCommandExecutor(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        this.handle(args, new BukkitCommandSender(sender));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        return new ArrayList<>(this.onTabComplete(args));
    }
}
