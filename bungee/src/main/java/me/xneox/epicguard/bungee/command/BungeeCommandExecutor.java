package me.xneox.epicguard.bungee.command;

import me.xneox.epicguard.core.command.GuardCommandExecutor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BungeeCommandExecutor extends Command implements TabExecutor {
    private final GuardCommandExecutor command; // why bungee... why Command is an abstract class?

    public BungeeCommandExecutor(GuardCommandExecutor command) {
        super("epicguard", "epicguard.admin", "guard", "eg", "ab", "antibot");
        this.command = command;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        this.command.handle(args, new BungeeSender(sender));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return this.command.onTabComplete(args);
    }
}
