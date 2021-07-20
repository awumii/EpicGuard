package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.Sender;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class BlacklistCommand implements SubCommand {
    @Override
    public void execute(@NotNull Sender<?> sender, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        MessagesConfiguration.Command config = epicGuard.messages().command();

        if (args.length != 3) {
            sender.sendMessage(config.prefix() + config.usage().replace("{USAGE}", "/guard blacklist <add/remove> <nickname/address>"));
            return;
        }

        if (args[1].equalsIgnoreCase("add")) {
            if (epicGuard.storageManager().isBlacklisted(args[2])) {
                sender.sendMessage(config.prefix() + config.alreadyBlacklisted().replace("{USER}", args[2]));
                return;
            }

            epicGuard.storageManager().blacklistPut(args[2]);
            sender.sendMessage(config.prefix() + config.blacklistAdd().replace("{USER}", args[2]));
        } else if (args[1].equalsIgnoreCase("remove")) {
            if (!epicGuard.storageManager().isBlacklisted(args[2])) {
                sender.sendMessage(config.prefix() + config.notBlacklisted().replace("{USER}", args[2]));
                return;
            }

            epicGuard.storageManager().removeBlacklist(args[2]);
            sender.sendMessage(config.prefix() + config.blacklistRemove().replace("{USER}", args[2]));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull String[] args, @NotNull EpicGuard epicGuard) {
        if (args.length == 2) {
            return Arrays.asList("add", "remove");
        }

        if (args[1].equalsIgnoreCase("remove")) {
            return epicGuard.storageManager().provider().addressBlacklist();
        }
        return new ArrayList<>();
    }
}
