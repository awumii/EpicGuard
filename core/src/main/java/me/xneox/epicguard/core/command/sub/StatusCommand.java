package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.Sender;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.OnlineUser;
import org.jetbrains.annotations.NotNull;

public class StatusCommand implements SubCommand {
    @Override
    public void execute(@NotNull Sender<?> sender, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        MessagesConfiguration.Command config = epicGuard.messages().command();

        if (!sender.isPlayer()) {
            sender.sendMessage("This command can't be run in console.");
            return;
        }

        OnlineUser onlineUser = epicGuard.userManager().getOrCreate(sender.uuid());
        onlineUser.notifications(!onlineUser.notifications());

        sender.sendMessage(config.prefix() + config.toggleStatus());
    }
}
