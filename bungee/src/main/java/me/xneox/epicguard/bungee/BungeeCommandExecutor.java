package me.xneox.epicguard.bungee;

import me.xneox.epicguard.core.command.CommandSubject;
import me.xneox.epicguard.core.command.EpicGuardCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BungeeCommandExecutor extends Command implements TabExecutor {
    private final EpicGuardCommand command;

    public BungeeCommandExecutor(EpicGuardCommand command) {
        super("epicguard", "epicguard.admin", "guard", "eg", "ab", "antibot");
        this.command = command;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        CommandSubject subject = new CommandSubject();
        if (sender instanceof ProxiedPlayer) {
            subject.setUUID(((ProxiedPlayer) sender).getUniqueId());
        }

        this.command.onCommand(args, new CommandSubject());
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return this.command.onTabComplete(args);
    }
}
