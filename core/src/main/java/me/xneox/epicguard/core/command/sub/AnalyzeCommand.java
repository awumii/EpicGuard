package me.xneox.epicguard.core.command.sub;

import com.google.common.net.InetAddresses;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.Sender;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.PendingUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AnalyzeCommand implements SubCommand {
    @Override
    public void execute(@NotNull Sender<?> sender, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        MessagesConfiguration.Command config = epicGuard.messages().command();

        if (args.length != 2) {
            sender.sendMessage(config.prefix() + config.usage().replace("{USAGE}", "/guard analyze <nickname/address>"));
            return;
        }

        String address = null;
        if (InetAddresses.isInetAddress(args[1])) {
            address = args[1];
        } else {
            String possibleAddress = epicGuard.storageManager().findByNickname(args[1]);
            if (possibleAddress != null) {
                address = possibleAddress;
            }
        }

        if (address == null) {
            sender.sendMessage(config.prefix() + config.analysisFailed());
            return;
        }

        List<String> accounts = epicGuard.storageManager().accounts(new PendingUser(address, null));

        for (String line : config.analyzeCommand()) {
            sender.sendMessage(line
                    .replace("{ADDRESS}", address)
                    .replace("{COUNTRY}", epicGuard.geoManager().countryCode(address))
                    .replace("{CITY}", epicGuard.geoManager().city(address))
                    .replace("{WHITELISTED}", epicGuard.storageManager().isWhitelisted(address) ? "&a✔" : "&c✖")
                    .replace("{BLACKLISTED}", epicGuard.storageManager().isBlacklisted(address) ? "&a✔" : "&c✖")
                    .replace("{ACCOUNT-AMOUNT}", String.valueOf(accounts.size()))
                    .replace("{NICKNAMES}", String.join(", ", accounts)));
        }
    }
}
