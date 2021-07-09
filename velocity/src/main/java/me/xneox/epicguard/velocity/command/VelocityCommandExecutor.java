package me.xneox.epicguard.velocity.command;

import com.velocitypowered.api.command.SimpleCommand;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.CommandExecutor;

import java.util.List;

public class VelocityCommandExecutor extends CommandExecutor implements SimpleCommand {
    public VelocityCommandExecutor(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public void execute(Invocation invocation) {
        this.handle(invocation.arguments(), new VelocityCommandSender(invocation.source()));
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return (List<String>) this.onTabComplete(invocation.arguments());
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("epicguard.admin");
    }
}
