package me.xneox.epicguard.core.command;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.User;
import me.xneox.epicguard.core.util.ChatUtils;
import me.xneox.epicguard.core.util.VersionUtils;
import org.diorite.libs.org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class EpicGuardCommand {
    private final EpicGuard epicGuard;

    public EpicGuardCommand(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    public void onCommand(@Nonnull String[] args, @Nonnull CommandSubject subject) {
        Validate.notNull(args, "Command arguments cannot be null!");
        Validate.notNull(args, "Command subject cannot be null!");

        MessagesConfiguration config = this.epicGuard.getMessages();
        String prefix = this.epicGuard.getMessages().prefix;
        if (args.length < 1) {
            send(subject, "");
            send(subject, " &c&lEpicGuard &7(&f" + this.epicGuard.getPlugin().getVersion() + "&7) &8- &7Command List");
            send(subject, "");
            send(subject, "  &7/guard stats &8- &7show plugin statistics.");
            send(subject, "  &7/guard notifications &8- &7enable actionbar notifications.");
            send(subject, "  &7/guard reload &8- &7reload config and messages.");
            send(subject, "  &7/guard whitelist &8<&7add&7/&7remove&8> &8<&7address&8> &8- &7whitelist/unwhitelist an address.");
            send(subject, "  &7/guard blacklist &8<&7add&7/&7remove&8> &8<&7address&8> &8- &7blacklist/unblacklist an address.");
            send(subject, "");
            return;
        }

        switch (args[0]) {
            case "stats":
                send(subject, "");
                send(subject, " &c&lEpicGuard &7(&f" + this.epicGuard.getPlugin().getVersion() + "&7) &8- &7Statistics");
                send(subject, "");
                send(subject, "  &7Blacklisted IPs: &c" + this.epicGuard.getStorageManager().getBlacklist().size());
                send(subject, "  &7Whitelisted IPs: &a" + this.epicGuard.getStorageManager().getWhitelist().size());
                send(subject, "  &7Connections: &b" + this.epicGuard.getAttackManager().getCPS() + "/s");
                send(subject, "  &7Total connections: &b" + this.epicGuard.getAttackManager().getTotalBots());
                send(subject, "");
                break;
            case "notifications":
                if (subject.isConsole()) {
                    send(subject, "You are not a player!");
                } else {
                    User user = this.epicGuard.getUserManager().getUser(subject.getUUID());
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
            default:
                send(subject, config.unknown);
        }
    }

    @Nullable
    public Collection<String> onTabComplete(@Nonnull String[] args) {
        Validate.notNull(args, "Command arguments cannot be null!");
        Validate.notNull(args, "Command subject cannot be null!");

        if (args.length == 0) {
            return Arrays.asList("stats", "notifications", "reload", "whitelist", "blacklist");
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
