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

public class WhitelistCommand implements SubCommand {
    @Override
    public void execute(@NotNull Sender<?> sender, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        MessagesConfiguration.Command config = epicGuard.messages().command();

        if (args.length != 3) {
            sender.sendMessage(config.prefix() + config.usage().replace("{USAGE}", "/guard whitelist <add/remove> <nickname/address>"));
            return;
        }

        AddressMeta meta = epicGuard.storageManager().resolveAddressMeta(args[2]);
        if (meta == null) {
            sender.sendMessage(config.prefix() + config.invalidArgument());
            return;
        }

        if (args[1].equalsIgnoreCase("add")) {
            if (meta.whitelisted()) {
                sender.sendMessage(config.prefix() + config.alreadyWhitelisted().replace("{USER}", args[2]));
                return;
            }

            meta.whitelisted(true);
            sender.sendMessage(config.prefix() + config.whitelistAdd().replace("{USER}", args[2]));
        } else if (args[1].equalsIgnoreCase("remove")) {
            if (!meta.whitelisted()) {
                sender.sendMessage(config.prefix() + config.notWhitelisted().replace("{USER}", args[2]));
                return;
            }

            meta.whitelisted(false);
            sender.sendMessage(config.prefix() + config.whitelistRemove().replace("{USER}", args[2]));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull String[] args, @NotNull EpicGuard epicGuard) {
        if (args.length == 2) {
            return Arrays.asList("add", "remove");
        }

        if (args[1].equalsIgnoreCase("remove")) {
            return epicGuard.storageManager().viewAddresses(AddressMeta::whitelisted);
        }
        return new ArrayList<>();
    }
}
