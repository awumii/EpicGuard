package me.xneox.epicguard.velocity;

import com.velocitypowered.api.command.SimpleCommand;
import me.xneox.epicguard.core.EpicGuard;

import java.util.List;

public class VelocityCommandExecutor implements SimpleCommand {
    private final EpicGuard epicGuard;

    public VelocityCommandExecutor(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public void execute(Invocation invocation) {
        this.epicGuard.commandHandler().handleCommand(invocation.arguments(), invocation.source());
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return (List<String>) this.epicGuard.commandHandler().handleSuggestions(invocation.arguments());
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("epicguard.admin");
    }
}
