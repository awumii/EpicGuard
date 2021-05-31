package me.xneox.epicguard.core.command;

import com.google.common.net.InetAddresses;
import org.apache.commons.lang3.Validate;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.PendingUser;
import me.xneox.epicguard.core.user.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This class is a platform-independent command handler.
 * See {@link Sender}.
 */
public class GuardCommandExecutor {
    private final EpicGuard epicGuard;

    public GuardCommandExecutor(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void handle(@Nonnull String[] args, @Nonnull Sender<?> sender) {
        Validate.notNull(args, "Command arguments cannot be null!");
        Validate.notNull(args, "Command subject cannot be null!");

        MessagesConfiguration.Command config = this.epicGuard.messages().command();
        String prefix = this.epicGuard.messages().command().prefix();

        if (args.length < 1) {
            for (String line : config.mainCommand()) {
                sender.sendMessage(line
                        .replace("{VERSION}", this.epicGuard.platform().version())
                        .replace("{BLACKLISTED-IPS}", String.valueOf(this.epicGuard.storageManager().provider().addressBlacklist().size()))
                        .replace("{WHITELISTED-IPS}", String.valueOf(this.epicGuard.storageManager().provider().addressWhitelist().size()))
                        .replace("{BLACKLISTED-NAMES}", String.valueOf(this.epicGuard.storageManager().provider().nameBlacklist().size()))
                        .replace("{WHITELISTED-NAMES}", String.valueOf(this.epicGuard.storageManager().provider().nameWhitelist().size()))
                        .replace("{CPS}", String.valueOf(this.epicGuard.attackManager().connectionCounter()))
                        .replace("{ATTACK}", this.epicGuard.attackManager().isAttack() ? "&a✔" : "&c✖"));
            }
            return;
        }

        switch (args[0]) {
            case "status":
                if (sender.isPlayer()) {
                    User user = this.epicGuard.userManager().getOrCreate(sender.uuid());
                    user.notifications(!user.notifications());
                    sender.sendMessage(prefix + config.toggleStatus());
                } else {
                    sender.sendMessage("This command can't be run in console.");
                }
                break;
            case "reload":
                sender.sendMessage(prefix + config.reloaded());
                this.epicGuard.loadConfigurations();
                break;
            case "whitelist":
                if (args.length != 3) {
                    sender.sendMessage(prefix + config.usage().replace("{USAGE}", "/guard whitelist <add/remove> <nickname/address>"));
                    return;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.storageManager().isWhitelisted(args[2])) {
                        sender.sendMessage(prefix + config.alreadyWhitelisted().replace("{USER}", args[2]));
                    } else {
                        sender.sendMessage(prefix + config.whitelistAdd().replace("{USER}", args[2]));
                        this.epicGuard.storageManager().whitelistPut(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.storageManager().isWhitelisted(args[2])) {
                        sender.sendMessage(prefix + config.whitelistRemove().replace("{USER}", args[2]));
                        this.epicGuard.storageManager().removeWhitelist(args[2]);
                    } else {
                        sender.sendMessage(prefix + config.notWhitelisted().replace("{USER}", args[2]));
                    }
                }
                break;
            case "blacklist":
                if (args.length != 3) {
                    sender.sendMessage(prefix + config.usage().replace("{USAGE}", "/guard blacklist <add/remove> <nickname/address>"));
                    return;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.storageManager().isBlacklisted(args[2])) {
                        sender.sendMessage(prefix + config.alreadyBlacklisted().replace("{USER}", args[2]));
                    } else {
                        sender.sendMessage(prefix + config.blacklistAdd().replace("{USER}", args[2]));
                        this.epicGuard.storageManager().blacklistPut(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.storageManager().isBlacklisted(args[2])) {
                        sender.sendMessage(prefix + config.blacklistRemove().replace("{USER}", args[2]));
                        this.epicGuard.storageManager().removeBlacklist(args[2]);
                    } else {
                        sender.sendMessage(prefix + config.notBlacklisted().replace("{USER}", args[2]));
                    }
                }
                break;
            case "analyze":
                if (args.length != 2) {
                    sender.sendMessage(prefix + config.usage().replace("{USAGE}", "/guard analyze <nickname/address>"));
                    return;
                }

                String address = null;
                if (InetAddresses.isInetAddress(args[1])) {
                    address = args[1];
                } else {
                    String possibleAddress = this.epicGuard.storageManager().findByNickname(args[1]);
                    if (possibleAddress != null) {
                        address = possibleAddress;
                    }
                }

                if (address == null) {
                    sender.sendMessage(prefix + config.analysisFailed());
                    return;
                }

                List<String> accounts = this.epicGuard.storageManager().accounts(new PendingUser(address, null));

                for (String line : config.analyzeCommand()) {
                    sender.sendMessage(line
                            .replace("{ADDRESS}", address)
                            .replace("{COUNTRY}", this.epicGuard.geoManager().countryCode(address))
                            .replace("{CITY}", this.epicGuard.geoManager().city(address))
                            .replace("{WHITELISTED}", this.epicGuard.storageManager().isWhitelisted(address) ? "&a✔" : "&c✖")
                            .replace("{BLACKLISTED}", this.epicGuard.storageManager().isBlacklisted(address) ? "&a✔" : "&c✖")
                            .replace("{ACCOUNT-AMOUNT}", String.valueOf(accounts.size()))
                            .replace("{NICKNAMES}", String.join(", ", accounts)));
                }
                break;
            default:
                sender.sendMessage(prefix + config.unknownCommand());
        }
    }

    @Nullable
    public Collection<String> onTabComplete(@Nonnull String[] args) {
        Validate.notNull(args, "Command arguments cannot be null!");
        Validate.notNull(args, "Command subject cannot be null!");

        if (args.length == 1) {
            return Arrays.asList("stats", "status", "analyze", "reload", "whitelist", "blacklist");
        }

        if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length == 2) {
                return Arrays.asList("add", "remove");
            }

            if (args[1].equalsIgnoreCase("remove")) {
                return this.epicGuard.storageManager().provider().addressWhitelist();
            }
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length == 2) {
                return Arrays.asList("add", "remove");
            }

            if (args[1].equalsIgnoreCase("remove")) {
                return this.epicGuard.storageManager().provider().addressBlacklist();
            }
        }
        return null;
    }
}
