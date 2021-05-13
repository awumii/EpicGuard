package me.xneox.epicguard.core.command;

import com.google.common.net.InetAddresses;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.PendingUser;
import me.xneox.epicguard.core.user.User;
import me.xneox.epicguard.core.util.ChatUtils;
import org.diorite.libs.org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * This class is a platform-independent command handler.
 * See {@link Sender}.
 */
public class CommandHandler {
    private final EpicGuard epicGuard;

    public CommandHandler(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void handle(@Nonnull String[] args, @Nonnull Sender<?> sender) {
        Validate.notNull(args, "Command arguments cannot be null!");
        Validate.notNull(args, "Command subject cannot be null!");

        MessagesConfiguration config = this.epicGuard.getMessages();
        String prefix = this.epicGuard.getMessages().prefix;
        if (args.length < 1) {
            for (String line : config.mainCommand) {
                send(sender, line
                        .replace("{VERSION}", this.epicGuard.getPlatform().getVersion())
                        .replace("{BLACKLISTED}", String.valueOf(this.epicGuard.getStorageManager().getProvider().getAddressBlacklist().size()))
                        .replace("{WHITELISTED}", String.valueOf(this.epicGuard.getStorageManager().getProvider().getAddressWhitelist().size()))
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
                    send(sender, prefix + config.notifications);
                } else {
                    send(sender, "This command can't be run in console.");
                }
                break;
            case "reload":
                send(sender, prefix + config.reload);
                this.epicGuard.reloadConfig();
                break;
            case "whitelist":
                if (args.length != 3) {
                    send(sender, prefix + config.usage.replace("{USAGE}", "/guard whitelist <add/remove> <nickname/address>"));
                    return;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.getStorageManager().isWhitelisted(args[2])) {
                        send(sender, prefix + config.alreadyWhitelisted.replace("{USER}", args[2]));
                    } else {
                        send(sender, prefix + config.whitelisted.replace("{USER}", args[2]));
                        this.epicGuard.getStorageManager().whitelist(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.getStorageManager().isWhitelisted(args[2])) {
                        send(sender, prefix + config.unWhitelisted.replace("{USER}", args[2]));
                        this.epicGuard.getStorageManager().removeFromWhitelist(args[2]);
                    } else {
                        send(sender, prefix + config.notWhitelisted.replace("{USER}", args[2]));
                    }
                }
                break;
            case "blacklist":
                if (args.length != 3) {
                    send(sender, prefix + config.usage.replace("{USAGE}", "/guard blacklist <add/remove> <nickname/address>"));
                    return;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.getStorageManager().isBlacklisted(args[2])) {
                        send(sender, prefix + config.alreadyBlacklisted.replace("{USER}", args[2]));
                    } else {
                        send(sender, prefix + config.blacklisted.replace("{USER}", args[2]));
                        this.epicGuard.getStorageManager().blacklist(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.getStorageManager().isBlacklisted(args[2])) {
                        send(sender, prefix + config.unBlacklisted.replace("{USER}", args[2]));
                        this.epicGuard.getStorageManager().removeFromBlacklist(args[2]);
                    } else {
                        send(sender, prefix + config.notBlacklisted.replace("{USER}", args[2]));
                    }
                }
                break;
            case "analyze":
                if (args.length != 2) {
                    send(sender, prefix + config.usage.replace("{USAGE}", "/guard analyze <nickname/address>"));
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
                    send(sender, prefix + config.analysisFailed);
                    return;
                }

                List<String> accounts = this.epicGuard.getStorageManager().getAccounts(new PendingUser(address, null));

                for (String line : config.analyzeCommand) {
                    send(sender, line
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
                send(sender, prefix + config.unknown);
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

    private void send(Sender<?> sender, String message) {
        sender.sendMessage(ChatUtils.colored(message));
    }
}
