package me.xneox.epicguard.core.command.sub;

import com.google.common.net.InetAddresses;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.command.Sender;
import me.xneox.epicguard.core.command.SubCommand;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.storage.AddressMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class AnalyzeCommand implements SubCommand {
    @Override
    public void execute(@NotNull Sender<?> sender, @NotNull String[] args, @NotNull EpicGuard epicGuard) {
        MessagesConfiguration.Command config = epicGuard.messages().command();

        if (args.length != 2) {
            sender.sendMessage(config.prefix() + config.usage().replace("{USAGE}", "/guard analyze <nickname/address>"));
            return;
        }

        AddressMeta meta = epicGuard.storageManager().resolveAddressMeta(args[1]);
        if (meta == null) {
            sender.sendMessage(config.prefix() + config.invalidArgument());
            return;
        }

        //noinspection UnstableApiUsage
        String address = InetAddresses.isInetAddress(args[1]) ? args[1] : epicGuard.storageManager().addresses().inverse().get(meta);

        for (String line : config.analyzeCommand()) {
            sender.sendMessage(line
                    .replace("{ADDRESS}", address)
                    .replace("{COUNTRY}", epicGuard.geoManager().countryCode(address))
                    .replace("{CITY}", epicGuard.geoManager().city(address))
                    .replace("{WHITELISTED}", meta.whitelisted() ? "&a✔" : "&c✖")
                    .replace("{BLACKLISTED}", meta.blacklisted() ? "&a✔" : "&c✖")
                    .replace("{ACCOUNT-AMOUNT}", String.valueOf(meta.nicknames().size()))
                    .replace("{NICKNAMES}", String.join(", ", meta.nicknames())));
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull String[] args, @NotNull EpicGuard epicGuard) {
        return epicGuard.storageManager().addresses().keySet();
    }
}
