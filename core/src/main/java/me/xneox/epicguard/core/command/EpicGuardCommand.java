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

public class EpicGuardCommand {
    private final EpicGuard epicGuard;

    public EpicGuardCommand(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void onCommand(@Nonnull String[] args, @Nonnull CommandSubject subject) {
        Validate.notNull(args, "Command arguments cannot be null!");
        Validate.notNull(args, "Command subject cannot be null!");

        MessagesConfiguration config = this.epicGuard.getMessages();
        String prefix = this.epicGuard.getMessages().prefix;
        if (args.length < 1) {
            send(subject, "&3&m--&8&m------------------------------------------&3&m--");
            send(subject, "&6&lEpicGuard &7(&f" + this.epicGuard.getPlugin().getVersion() + "&7) &8- &7Command List");
            send(subject, "&e/guard stats &b- &7show plugin statistics.");
            send(subject, "&e/guard notifications &b- &7enable actionbar notifications.");
            send(subject, "&e/guard reload &b- &7reload config and messages.");
            send(subject, "&e/guard analyze <address> &b- &7see details about the specified address.");
            send(subject, "&e/guard whitelist &8<&7add&7/&7remove&8> &8<&7address&8> &b- &7whitelist/unwhitelist an address.");
            send(subject, "&e/guard blacklist &8<&7add&7/&7remove&8> &8<&7address&8> &b- &7blacklist/unblacklist an address.");
            send(subject, "&3&m--&8&m------------------------------------------&3&m--");
            return;
        }

        switch (args[0]) {
            case "stats":
                send(subject, "&3&m--&8&m------------------------------------------&3&m--");
                send(subject, "&6&lEpicGuard &7(&f" + this.epicGuard.getPlugin().getVersion() + "&7) &8- &7Statistics");
                send(subject, "&eBlacklisted IPs: &7" + this.epicGuard.getStorageManager().getBlacklist().size());
                send(subject, "&eWhitelisted IPs: &7" + this.epicGuard.getStorageManager().getWhitelist().size());
                send(subject, "&eConnections: &7" + this.epicGuard.getAttackManager().getCPS() + "/s");
                send(subject, "&eTotal connections: &7" + this.epicGuard.getAttackManager().getTotalBots());
                send(subject, "&3&m--&8&m------------------------------------------&3&m--");
                break;
            case "notifications":
                if (subject.isConsole()) {
                    send(subject, "You are not a player!");
                } else {
                    User user = this.epicGuard.getUserManager().getOrCreate(subject.getUUID());
                    user.setNotifications(!user.hasNotifications());
                    send(subject, prefix + config.notifications);
                }
                break;
            case "reload":
                send(subject, prefix + config.reload);
                this.epicGuard.reloadConfig();
                break;
            case "whitelist":
                if (args.length != 3) {
                    send(subject, prefix + config.usage.replace("{USAGE}", "/guard whitelist <add/remove> <address>"));
                    return;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.getStorageManager().isWhitelisted(args[2])) {
                        send(subject, prefix + config.alreadyWhitelisted.replace("{IP}", args[2]));
                    } else {
                        send(subject, prefix + config.whitelisted.replace("{IP}", args[2]));
                        this.epicGuard.getStorageManager().whitelist(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.getStorageManager().isWhitelisted(args[2])) {
                        send(subject, prefix + config.unWhitelisted.replace("{IP}", args[2]));
                        this.epicGuard.getStorageManager().getWhitelist().remove(args[2]);
                    } else {
                        send(subject, prefix + config.notWhitelisted.replace("{IP}", args[2]));
                    }
                }
                break;
            case "blacklist":
                if (args.length != 3) {
                    send(subject, prefix + config.usage.replace("{USAGE}", "/guard blacklist <add/remove> <address>"));
                    return;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.getStorageManager().isBlacklisted(args[2])) {
                        send(subject, prefix + config.alreadyBlacklisted.replace("{IP}", args[2]));
                    } else {
                        send(subject, prefix + config.blacklisted.replace("{IP}", args[2]));
                        this.epicGuard.getStorageManager().blacklist(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.getStorageManager().isBlacklisted(args[2])) {
                        send(subject, prefix + config.unBlacklisted.replace("{IP}", args[2]));
                        this.epicGuard.getStorageManager().getBlacklist().remove(args[2]);
                    } else {
                        send(subject, prefix + config.notBlacklisted.replace("{IP}", args[2]));
                    }
                }
                break;
            case "analyze":
                if (args.length != 2) {
                    send(subject, prefix + config.usage.replace("{USAGE}", "/guard analyze <address>"));
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
                    send(subject, prefix + config.analysisFailed);
                    return;
                }

                send(subject, "&3&m--&8&m------------------------------------------&3&m--");
                send(subject, "&6&lEpicGuard Analysis of " + address);
                send(subject, "&eCountry: &7" + this.epicGuard.getGeoManager().getCountryCode(address));
                send(subject, "&eCity: &7" + this.epicGuard.getGeoManager().getCity(address));
                send(subject, "");
                send(subject, "&eWhitelisted: &7" + (this.epicGuard.getStorageManager().isWhitelisted(address) ? "&a&l✔" : "&c&l✖"));
                send(subject, "&eBlacklisted: &7" + (this.epicGuard.getStorageManager().isBlacklisted(address) ? "&a&l✔" : "&c&l✖"));
                send(subject, "");

                List<String> accounts = this.epicGuard.getStorageManager().getAccounts(new BotUser(address, null));
                send(subject, "&eSeen nicknames (" + accounts.size() + "): &7" + String.join(", ", accounts));

                send(subject, "");
                send(subject, "&eMore details: &7&nhttps://proxycheck.io/threats/" + address);
                send(subject, "&3&m--&8&m------------------------------------------&3&m--");
                break;
            default:
                send(subject, prefix + config.unknown);
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

    private void send(CommandSubject subject, String message) {
        this.epicGuard.getPlugin().sendMessage(ChatUtils.colored(message), subject);
    }
}
