package me.xneox.epicguard.core.command;

import com.google.common.net.InetAddresses;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.BotUser;
import me.xneox.epicguard.core.user.User;
import me.xneox.epicguard.core.util.ChatUtils;
import org.diorite.libs.org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CommandExecutor {
    private final EpicGuard epicGuard;

    public CommandExecutor(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void onCommand(@Nonnull String[] args, @Nonnull Sender<?> sender) {
        Validate.notNull(args, "Command arguments cannot be null!");
        Validate.notNull(args, "Command subject cannot be null!");

        MessagesConfiguration config = this.epicGuard.getMessages();
        String prefix = this.epicGuard.getMessages().prefix;
        if (args.length < 1) {
            send(sender, "&3&m--&8&m------------------------------------------&3&m--");
            send(sender, "&6&lEpicGuard &7(&f" + this.epicGuard.getPlugin().getVersion() + "&7) &8- &7Command List");
            send(sender, "&e/guard stats &b- &7show plugin statistics.");
            send(sender, "&e/guard notifications &b- &7enable actionbar notifications.");
            send(sender, "&e/guard reload &b- &7reload config and messages.");
            send(sender, "&e/guard analyze <nickname/address> &b- &7see details about the specified address.");
            send(sender, "&e/guard whitelist &8<&7add&7/&7remove&8> &8<&7nickname/address&8> &b- &7whitelist/unwhitelist an address or nickname.");
            send(sender, "&e/guard blacklist &8<&7add&7/&7remove&8> &8<&7nickname/address&8> &b- &7blacklist/unblacklist an address or nickname.");
            send(sender, "&3&m--&8&m------------------------------------------&3&m--");
            return;
        }

        switch (args[0]) {
            case "stats":
                send(sender, "&3&m--&8&m------------------------------------------&3&m--");
                send(sender, "&6&lEpicGuard &7(&f" + this.epicGuard.getPlugin().getVersion() + "&7) &8- &7Statistics");
                send(sender, "&eBlacklisted IPs: &7" + this.epicGuard.getStorageManager().getBlacklist().size());
                send(sender, "&eWhitelisted IPs: &7" + this.epicGuard.getStorageManager().getWhitelist().size());
                send(sender, "&eConnections: &7" + this.epicGuard.getAttackManager().getCPS() + "/s");
                send(sender, "&eTotal connections: &7" + this.epicGuard.getAttackManager().getTotalBots());
                send(sender, "&3&m--&8&m------------------------------------------&3&m--");
                break;
            case "notifications":
                if (sender.isPlayer()) {
                    User user = this.epicGuard.getUserManager().getOrCreate(sender.getUUID());
                    user.setNotifications(!user.hasNotifications());
                    send(sender, prefix + config.notifications);
                } else {
                    send(sender, "You are not a player!");
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

                send(sender, "&3&m--&8&m------------------------------------------&3&m--");
                send(sender, "&6&lEpicGuard Analysis of " + address);
                send(sender, "&eCountry: &7" + this.epicGuard.getGeoManager().getCountryCode(address));
                send(sender, "&eCity: &7" + this.epicGuard.getGeoManager().getCity(address));
                send(sender, "");
                send(sender, "&eWhitelisted: &7" + (this.epicGuard.getStorageManager().isWhitelisted(address) ? "&a&l✔" : "&c&l✖"));
                send(sender, "&eBlacklisted: &7" + (this.epicGuard.getStorageManager().isBlacklisted(address) ? "&a&l✔" : "&c&l✖"));
                send(sender, "");

                List<String> accounts = this.epicGuard.getStorageManager().getAccounts(new BotUser(address, null));
                send(sender, "&eSeen nicknames (" + accounts.size() + "): &7" + String.join(", ", accounts));

                send(sender, "");
                send(sender, "&eMore details: &7&nhttps://proxycheck.io/threats/" + address);
                send(sender, "&3&m--&8&m------------------------------------------&3&m--");
                break;
            default:
                send(sender, prefix + config.unknown);
        }
    }

    @Nullable
    public Collection<String> onTabComplete(@Nonnull String[] args) {
        Validate.notNull(args, "Command arguments cannot be null!");
        Validate.notNull(args, "Command subject cannot be null!");

        if (args.length == 0) {
            return Arrays.asList("stats", "notifications", "analyze", "reload", "whitelist", "blacklist");
        } else if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length == 1) {
                return Arrays.asList("add", "remove");
            } else if (args[1].equalsIgnoreCase("remove")) {
                return this.epicGuard.getStorageManager().getWhitelist();
            }
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length == 1) {
                return Arrays.asList("add", "remove");
            } else if (args[1].equalsIgnoreCase("remove")) {
                return this.epicGuard.getStorageManager().getBlacklist();
            }
        }
        return Collections.emptyList();
    }

    private void send(Sender<?> sender, String message) {
        sender.sendMessage(ChatUtils.colored(message));
    }
}
