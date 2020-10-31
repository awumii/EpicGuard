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
            send(subject, "&8&m---------------------------------------------------");
            send(subject, "  &6&lEpicGuard &8(BungeeCord version)");
            send(subject, "  &7Version: &f" + this.epicGuard.getPlugin().getVersion());
            if (VersionUtils.isAvailable()) {
                send(subject, "");
                send(subject, "  &7New version is available: &c&n" + VersionUtils.getRemoteVersion());
                send(subject, "  &c&ohttps://www.spigotmc.org/resources/72369/");
            }
            send(subject, "");
            send(subject, " &8• &b/guard stats &7- show plugin statistics.");
            send(subject, " &8• &b/guard notifications &7- enable actionbar notifications.");
            send(subject, " &8• &b/guard reload &7- reload config and messages.");
            send(subject, " &8• &b/guard whitelist <add/remove> <address> &7- whitelist an address.");
            send(subject, " &8• &b/guard blacklist <add/remove> <address> &7- blacklist an address.");
            send(subject, "&8&m---------------------------------------------------");
            return;
        }

        switch (args[0]) {
            case "stats":
                send(subject, "&8&m---------------------------------------------------");
                send(subject, "  &6&lEpicGuard &7(Statistics)");
                send(subject, "  &7Version: &f" + this.epicGuard.getPlugin().getVersion());
                if (VersionUtils.isAvailable()) {
                    send(subject, "");
                    send(subject, "  &7New version is available: &c&n" + VersionUtils.getRemoteVersion());
                    send(subject, "  &c&ohttps://www.spigotmc.org/resources/72369/");
                }
                send(subject, "");
                send(subject, " &8• &7Blacklisted IPs: &c&l" + this.epicGuard.getStorageManager().getBlacklist().size());
                send(subject, " &8• &7Whitelisted IPs: &a&l" + this.epicGuard.getStorageManager().getWhitelist().size());
                send(subject, " &8• &7Connections: &6&l" + this.epicGuard.getAttackManager().getCPS() + "/s");
                send(subject, " &8• &7Total connections: &6&l" + this.epicGuard.getAttackManager().getTotalBots() + "/s");
                send(subject, "");
                send(subject, "&8&m---------------------------------------------------");
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

        // else if hell ahead, but this is still readable
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
        return null;
    }

    private void send(CommandSubject subject, String message) {
        this.epicGuard.getPlugin().sendMessage(ChatUtils.colored(message), subject);
    }
}
