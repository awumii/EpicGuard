package me.xneox.epicguard.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import me.xneox.epicguard.core.command.CommandExecutor;

import java.util.List;

public class VelocityCommandExecutor implements SimpleCommand {
    private final CommandExecutor command;

    public VelocityCommandExecutor(CommandExecutor command) {
        this.command = command;
    }

    @Override
    public void execute(Invocation invocation) {
        this.command.onCommand(invocation.arguments(), new VelocitySender(invocation.source()));
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
