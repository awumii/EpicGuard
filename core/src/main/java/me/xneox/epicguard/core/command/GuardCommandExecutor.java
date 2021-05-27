package me.xneox.epicguard.core.command;

import com.google.common.net.InetAddresses;
import de.leonhard.storage.util.Valid;
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
        Valid.notNull(args, "Command arguments cannot be null!");
        Valid.notNull(args, "Command subject cannot be null!");

        MessagesConfiguration.Command config = this.epicGuard.getMessages().command;
        String prefix = this.epicGuard.getMessages().command.prefix;

        if (args.length < 1) {
            for (String line : config.mainCommand) {
                sender.sendMessage(line
                        .replace("{VERSION}", this.epicGuard.getPlatform().getVersion())
                        .replace("{BLACKLISTED-IPS}", String.valueOf(this.epicGuard.getStorageManager().getProvider().getAddressBlacklist().size()))
                        .replace("{WHITELISTED-IPS}", String.valueOf(this.epicGuard.getStorageManager().getProvider().getAddressWhitelist().size()))
                        .replace("{BLACKLISTED-NAMES}", String.valueOf(this.epicGuard.getStorageManager().getProvider().getNameBlacklist().size()))
                        .replace("{WHITELISTED-NAMES}", String.valueOf(this.epicGuard.getStorageManager().getProvider().getNameWhitelist().size()))
                        .replace("{CPS}", String.valueOf(this.epicGuard.getAttackManager().getConnectionCounter()))
                        .replace("{ATTACK}", this.epicGuard.getAttackManager().isAttack() ? "&a✔" : "&c✖"));
            }
            return;
        }

        switch (args[0]) {
            case "status":
                if (sender.isPlayer()) {
                    User user = this.epicGuard.getUserManager().getOrCreate(sender.getUUID());
                    user.setNotifications(!user.hasNotifications());
                    sender.sendMessage(prefix + config.toggleStatus);
                } else {
                    sender.sendMessage("This command can't be run in console.");
                }
                break;
            case "reload":
                sender.sendMessage(prefix + config.reload);
                this.epicGuard.loadConfigurations();
                break;
            case "whitelist":
                if (args.length != 3) {
                    sender.sendMessage(prefix + config.usage.replace("{USAGE}", "/guard whitelist <add/remove> <nickname/address>"));
                    return;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.getStorageManager().isWhitelisted(args[2])) {
                        sender.sendMessage(prefix + config.alreadyWhitelisted.replace("{USER}", args[2]));
                    } else {
                        sender.sendMessage(prefix + config.whitelistAdd.replace("{USER}", args[2]));
                        this.epicGuard.getStorageManager().whitelistPut(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.getStorageManager().isWhitelisted(args[2])) {
                        sender.sendMessage(prefix + config.whitelistRemove.replace("{USER}", args[2]));
                        this.epicGuard.getStorageManager().removeWhitelist(args[2]);
                    } else {
                        sender.sendMessage(prefix + config.notWhitelisted.replace("{USER}", args[2]));
                    }
                }
                break;
            case "blacklist":
                if (args.length != 3) {
                    sender.sendMessage(prefix + config.usage.replace("{USAGE}", "/guard blacklist <add/remove> <nickname/address>"));
                    return;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.getStorageManager().isBlacklisted(args[2])) {
                        sender.sendMessage(prefix + config.alreadyBlacklisted.replace("{USER}", args[2]));
                    } else {
                        sender.sendMessage(prefix + config.blacklistAdd.replace("{USER}", args[2]));
                        this.epicGuard.getStorageManager().blacklistPut(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.getStorageManager().isBlacklisted(args[2])) {
                        sender.sendMessage(prefix + config.blacklistRemove.replace("{USER}", args[2]));
                        this.epicGuard.getStorageManager().removeBlacklist(args[2]);
                    } else {
                        sender.sendMessage(prefix + config.notBlacklisted.replace("{USER}", args[2]));
                    }
                }
                break;
            case "analyze":
                if (args.length != 2) {
                    sender.sendMessage(prefix + config.usage.replace("{USAGE}", "/guard analyze <nickname/address>"));
                    return;
                }

                String address = null;
                if (InetAddresses.isInetAddress(args[1])) {
                    address = args[1];
                } else {
                    String possibleAddress = this.epicGuard.getStorageManager().findByNickname(args[1]);
                    if (possibleAddress != null) {
                        address = possibleAddress;
                    }
                }

                if (address == null) {
                    sender.sendMessage(prefix + config.analysisFailed);
                    return;
                }

                List<String> accounts = this.epicGuard.getStorageManager().getAccounts(new PendingUser(address, null));

                for (String line : config.analyzeCommand) {
                    sender.sendMessage(line
                            .replace("{ADDRESS}", address)
                            .replace("{COUNTRY}", this.epicGuard.getGeoManager().getCountryCode(address))
                            .replace("{CITY}", this.epicGuard.getGeoManager().getCity(address))
                            .replace("{WHITELISTED}", this.epicGuard.getStorageManager().isWhitelisted(address) ? "&a✔" : "&c✖")
                            .replace("{BLACKLISTED}", this.epicGuard.getStorageManager().isBlacklisted(address) ? "&a✔" : "&c✖")
                            .replace("{ACCOUNT-AMOUNT}", String.valueOf(accounts.size()))
                            .replace("{NICKNAMES}", String.join(", ", accounts)));
                }
                break;
            default:
                sender.sendMessage(prefix + config.unknownCommand);
        }
    }

    @Nullable
    public Collection<String> onTabComplete(@Nonnull String[] args) {
        Valid.notNull(args, "Command arguments cannot be null!");
        Valid.notNull(args, "Command subject cannot be null!");

        if (args.length == 1) {
            return Arrays.asList("stats", "status", "analyze", "reload", "whitelist", "blacklist");
        }

        if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length == 2) {
                return Arrays.asList("add", "remove");
            }

            if (args[1].equalsIgnoreCase("remove")) {
                return this.epicGuard.getStorageManager().getProvider().getAddressWhitelist();
            }
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length == 2) {
                return Arrays.asList("add", "remove");
            }

            if (args[1].equalsIgnoreCase("remove")) {
                return this.epicGuard.getStorageManager().getProvider().getAddressBlacklist();
            }
        }
        return null;
    }
}
