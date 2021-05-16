package me.xneox.epicguard.bukkit.command;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.GuardCommandExecutor;
import org.bukkit.command.*;

import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class BukkitGuardCommandExecutor extends GuardCommandExecutor implements CommandExecutor, TabCompleter {
    public BukkitGuardCommandExecutor(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        this.handle(args, new BukkitSender(sender));
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String alias, @Nonnull String[] args) {
        return (List<String>) this.onTabComplete(args);
    }
}
