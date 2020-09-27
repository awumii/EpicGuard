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

package me.xneox.epicguard.bungee.command;

import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.config.MessagesConfiguration;
import me.xneox.epicguard.core.user.User;
import me.xneox.epicguard.core.util.ChatUtils;
import me.xneox.epicguard.core.util.UpdateChecker;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.Arrays;

public class EpicGuardCommand extends Command implements TabExecutor {
    private final EpicGuard epicGuard;

    public EpicGuardCommand(EpicGuard epicGuard) {
        super("epicguard", "epicguard.admin", "guard", "eg", "ab", "antibot");
        this.epicGuard = epicGuard;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        MessagesConfiguration config = this.epicGuard.getMessages();
        String prefix = this.epicGuard.getMessages().prefix;
        if (args.length < 1) {
            send(sender, "&8&m---------------------------------------------------");
            send(sender, "  &6&lEpicGuard &8(BungeeCord version)");
            send(sender, "  &7Version: &f" + this.epicGuard.getPlugin().getVersion());
            if (UpdateChecker.isAvailable()) {
                send(sender, "");
                send(sender, "  &7New version is available: &c&n" + UpdateChecker.getRemoteVersion());
                send(sender, "  &c&ohttps://www.spigotmc.org/resources/72369/");
            }
            send(sender, "");
            send(sender, " &8• &b/guard stats &7- show plugin statistics.");
            send(sender, " &8• &b/guard notifications &7- enable actionbar notifications.");
            send(sender, " &8• &b/guard reload &7- reload config and messages.");
            send(sender, " &8• &b/guard whitelist <address> &7- whitelist an address.");
            send(sender, " &8• &b/guard blacklist <address> &7- blacklist an address.");
            send(sender, "&8&m---------------------------------------------------");
            return;
        }

        switch (args[0]) {
            case "stats":
                send(sender, "&8&m---------------------------------------------------");
                send(sender, "  &6&lEpicGuard &7(Statistics)");
                send(sender, "  &7Version: &f" + this.epicGuard.getPlugin().getVersion());
                if (UpdateChecker.isAvailable()) {
                    send(sender, "");
                    send(sender, "  &7New version is available: &c&n" + UpdateChecker.getRemoteVersion());
                    send(sender, "  &c&ohttps://www.spigotmc.org/resources/72369/");
                }
                send(sender, "");
                send(sender, " &8• &7Blacklisted IPs: &c&l" + this.epicGuard.getStorageManager().getBlacklist().size());
                send(sender, " &8• &7Whitelisted IPs: &a&l" + this.epicGuard.getStorageManager().getWhitelist().size());
                send(sender, " &8• &7Connections: &6&l" + this.epicGuard.getConnectionPerSecond() + "/s");
                send(sender, "");
                send(sender, "&8&m---------------------------------------------------");
                break;
            case "notifications":
                ProxiedPlayer player = (ProxiedPlayer) sender;
                User user = this.epicGuard.getUserManager().getUser(player.getUniqueId());
                user.setNotifications(!user.isNotifications());
                send(sender, prefix + config.notifications);
                break;
            case "reload":
                send(sender, prefix + config.reload);
                this.epicGuard.reloadConfig();
                break;
            case "whitelist":
                if (args.length != 2) {
                    send(sender, prefix + config.usage.replace("{USAGE}", "/guard whitelist <address>"));
                    return;
                }
                send(sender, prefix + config.whitelisted.replace("{IP}", args[1]));
                this.epicGuard.getStorageManager().whitelist(args[1]);
                break;
            case "blacklist":
                if (args.length != 2) {
                    send(sender, prefix + config.usage.replace("{USAGE}", "/guard blacklist <address>"));
                    return;
                }
                send(sender, prefix + config.blacklisted.replace("{IP}", args[1]));
                this.epicGuard.getStorageManager().blacklist(args[1]);
                break;
            default:
                send(sender, config.unknown);
        }
    }

    private void send(CommandSender sender, String message) {
        sender.sendMessage(TextComponent.fromLegacyText(ChatUtils.colored(message)));
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] args) {
        if (args.length < 1) {
            return Arrays.asList("stats", "notifications", "reload", "whitelist", "blacklist");
        }
        return null;
    }
}
