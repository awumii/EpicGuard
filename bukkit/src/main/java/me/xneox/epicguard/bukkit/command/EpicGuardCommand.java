/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.bukkit.command;

import me.xneox.epicguard.bukkit.PlatformBukkit;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.User;
import me.xneox.epicguard.core.util.ChatUtils;
import me.xneox.epicguard.core.util.VersionUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class EpicGuardCommand implements CommandExecutor, TabCompleter {
    private final PlatformBukkit plugin;
    private final EpicGuard epicGuard;

    public EpicGuardCommand(PlatformBukkit plugin, EpicGuard epicGuard) {
        this.plugin = plugin;
        this.epicGuard = epicGuard;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        MessagesConfiguration config = this.epicGuard.getMessages();
        String prefix = this.epicGuard.getMessages().prefix;
        if (args.length < 1) {
            send(sender, "&8&m---------------------------------------------------");
            send(sender, "  &6&lEpicGuard &8(Spigot version)");
            send(sender, "  &7Version: &f" + this.epicGuard.getPlugin().getVersion());
            if (VersionUtils.isAvailable()) {
                send(sender, "");
                send(sender, "  &7New version is available: &c&n" + VersionUtils.getRemoteVersion());
                send(sender, "  &c&ohttps://www.spigotmc.org/resources/72369/");
            }
            send(sender, "");
            send(sender, " &8• &b/guard stats &7- show plugin statistics.");
            send(sender, " &8• &b/guard notifications &7- enable actionbar notifications.");
            send(sender, " &8• &b/guard reload &7- reload config and messages.");
            send(sender, " &8• &b/guard whitelist <add/remove> <address> &7- whitelist an address.");
            send(sender, " &8• &b/guard blacklist <add/remove> <address> &7- blacklist an address.");
            send(sender, "&8&m---------------------------------------------------");
            return true;
        }

        if (!sender.hasPermission("epicguard.admin")) {
            send(sender, config.noPermission);
            return true;
        }

        switch (args[0]) {
            case "stats":
                send(sender, "&8&m---------------------------------------------------");
                send(sender, "  &6&lEpicGuard &7(Statistics)");
                send(sender, "  &7Version: &f" + this.epicGuard.getPlugin().getVersion());
                if (VersionUtils.isAvailable()) {
                    send(sender, "");
                    send(sender, "  &7New version is available: &c&n" + VersionUtils.getRemoteVersion());
                    send(sender, "  &c&ohttps://www.spigotmc.org/resources/72369/");
                }
                send(sender, "");
                send(sender, " &8• &7Blacklisted IPs: &c&l" + this.epicGuard.getStorageManager().getBlacklist().size());
                send(sender, " &8• &7Whitelisted IPs: &a&l" + this.epicGuard.getStorageManager().getWhitelist().size());
                send(sender, " &8• &7Connections: &6&l" + this.epicGuard.getAttackManager().getCPS() + "/s");
                send(sender, " &8• &7Total connections: &6&l" + this.epicGuard.getAttackManager().getTotalBots() + "/s");
                send(sender, "");
                send(sender, "&8&m---------------------------------------------------");
                break;
            case "notifications":
                User user = this.epicGuard.getUserManager().getUser(((Player) sender).getUniqueId());
                user.setNotifications(!user.hasNotifications());
                send(sender, prefix + config.notifications);
                break;
            case "reload":
                send(sender, prefix + config.reload);
                this.epicGuard.reloadConfig();
                this.plugin.getModuleManager().load();
                break;
            case "whitelist":
                if (args.length != 3) {
                    send(sender, prefix + config.usage.replace("{USAGE}", "/guard whitelist <add/remove> <address>"));
                    return true;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.getStorageManager().isWhitelisted(args[2])) {
                        send(sender, prefix + config.alreadyWhitelisted.replace("{IP}", args[2]));
                    } else {
                        send(sender, prefix + config.whitelisted.replace("{IP}", args[2]));
                        this.epicGuard.getStorageManager().whitelist(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.getStorageManager().isWhitelisted(args[2])) {
                        send(sender, prefix + config.unWhitelisted.replace("{IP}", args[2]));
                        this.epicGuard.getStorageManager().getWhitelist().remove(args[2]);
                    } else {
                        send(sender, prefix + config.notWhitelisted.replace("{IP}", args[2]));
                    }
                }
                break;
            case "blacklist":
                if (args.length != 3) {
                    send(sender, prefix + config.usage.replace("{USAGE}", "/guard blacklist <add/remove> <address>"));
                    return true;
                }

                if (args[1].equalsIgnoreCase("add")) {
                    if (this.epicGuard.getStorageManager().isBlacklisted(args[2])) {
                        send(sender, prefix + config.alreadyBlacklisted.replace("{IP}", args[2]));
                    } else {
                        send(sender, prefix + config.blacklisted.replace("{IP}", args[2]));
                        this.epicGuard.getStorageManager().blacklist(args[2]);
                    }
                } else if (args[1].equalsIgnoreCase("remove")) {
                    if (this.epicGuard.getStorageManager().isBlacklisted(args[2])) {
                        send(sender, prefix + config.unBlacklisted.replace("{IP}", args[2]));
                        this.epicGuard.getStorageManager().getBlacklist().remove(args[2]);
                    } else {
                        send(sender, prefix + config.notBlacklisted.replace("{IP}", args[2]));
                    }
                }
                break;
            default:
                send(sender, config.unknown);
        }
        return true;
    }

    private void send(CommandSender sender, String message) {
        sender.sendMessage(ChatUtils.colored(message));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 1) {
            return Arrays.asList("stats", "notifications", "reload", "whitelist", "blacklist");
        }
        return null;
    }
}
