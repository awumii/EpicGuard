package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.Sender;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.User;
import org.jetbrains.annotations.NotNull;

public class StatusCommand implements SubCommand {
    @Override
    public void execute(@NotNull Sender<?> sender, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        MessagesConfiguration.Command config = epicGuard.messages().command();

        if (!sender.isPlayer()) {
            sender.sendMessage("This command can't be run in console.");
            return;
        }

        User user = epicGuard.userManager().getOrCreate(sender.uuid());
        user.notifications(!user.notifications());

        sender.sendMessage(config.prefix() + config.toggleStatus());
    }
}
