package me.xneox.epicguard.velocity;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import me.xneox.epicguard.core.command.CommandSubject;
import me.xneox.epicguard.core.command.EpicGuardCommand;

import javax.annotation.Nonnull;
import java.util.List;

public class VelocityCommandExecutor implements Command {
    private final EpicGuardCommand command;

    public VelocityCommandExecutor(EpicGuardCommand command) {
        this.command = command;
    }

    @Override
    public void execute(CommandSource source, @Nonnull String[] args) {
        CommandSubject subject = new CommandSubject();
        if (source instanceof Player) {
            subject.setUUID(((Player) source).getUniqueId());
        }

        this.command.onCommand(args, subject);
    }

    @Override
    public List<String> suggest(CommandSource source, @Nonnull String[] currentArgs) {
        return (List<String>) this.command.onTabComplete(currentArgs);
    }

    @Override
    public boolean hasPermission(CommandSource source, @Nonnull String[] args) {
        return source.hasPermission("epicguard.admin");
    }
}
