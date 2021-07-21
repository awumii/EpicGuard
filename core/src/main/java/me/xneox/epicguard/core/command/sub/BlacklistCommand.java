package me.xneox.epicguard.core.command.sub;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.Sender;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.storage.AddressMeta;
import org.jetbrains.annotations.NotNull;

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

        AddressMeta meta = epicGuard.storageManager().resolveAddressMeta(args[2]);
        if (meta == null) {
            sender.sendMessage(config.prefix() + config.invalidArgument());
            return;
        }

        if (args[1].equalsIgnoreCase("add")) {
            if (meta.blacklisted()) {
                sender.sendMessage(config.prefix() + config.alreadyBlacklisted().replace("{USER}", args[2]));
                return;
            }

            meta.blacklisted(true);
            sender.sendMessage(config.prefix() + config.blacklistAdd().replace("{USER}", args[2]));
        } else if (args[1].equalsIgnoreCase("remove")) {
            if (!meta.blacklisted()) {
                sender.sendMessage(config.prefix() + config.notBlacklisted().replace("{USER}", args[2]));
                return;
            }

            meta.blacklisted(false);
            sender.sendMessage(config.prefix() + config.blacklistRemove().replace("{USER}", args[2]));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull String[] args, @NotNull EpicGuard epicGuard) {
        if (args.length == 2) {
            return Arrays.asList("add", "remove");
        }

        if (args[1].equalsIgnoreCase("remove")) {
            return epicGuard.storageManager().viewAddresses(AddressMeta::blacklisted);
        }
        return new ArrayList<>();
    }
}
