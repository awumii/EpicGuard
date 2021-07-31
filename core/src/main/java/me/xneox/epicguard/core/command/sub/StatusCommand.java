package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.OnlineUser;
import me.xneox.epicguard.core.util.MessageUtils;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class StatusCommand implements SubCommand {
    @Override
    public void execute(@NotNull Audience audience, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        MessagesConfiguration.Command config = epicGuard.messages().command();

        Optional<UUID> optional = audience.get(Identity.UUID);
        optional.ifPresentOrElse(uuid -> {
            OnlineUser onlineUser = epicGuard.userManager().getOrCreate(uuid);
            onlineUser.notifications(!onlineUser.notifications());

            audience.sendMessage(MessageUtils.component(config.prefix() + config.toggleStatus()));
        }, () -> audience.sendMessage(Component.text("This command can't be run in console.")));
    }
}
