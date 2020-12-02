package me.xneox.epicguard.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import me.xneox.epicguard.core.command.CommandSubject;
import me.xneox.epicguard.core.command.EpicGuardCommand;

import java.util.List;

public class VelocityCommandExecutor implements SimpleCommand {
    private final EpicGuardCommand command;

    public VelocityCommandExecutor(EpicGuardCommand command) {
        this.command = command;
    }

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        CommandSubject subject = new CommandSubject();
        if (source instanceof Player) {
            subject.setUUID(((Player) source).getUniqueId());
        }

        this.command.onCommand(args, subject);
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return (List<String>) this.command.onTabComplete(invocation.arguments());
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("epicguard.admin");
    }
}
