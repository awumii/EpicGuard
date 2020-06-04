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

package me.ishift.epicguard.bungee.command;

import me.ishift.epicguard.bungee.EpicGuardBungee;
import me.ishift.epicguard.bungee.util.BungeeNotify;
import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.common.AttackManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GuardCommand extends Command {
    private final AttackManager manager;
    private final EpicGuardBungee plugin;

    public GuardCommand(String name, EpicGuardBungee plugin) {
        super(name, "epicguard.admin");
        this.manager = plugin.getManager();
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            BungeeUtil.sendMessage(sender, "&8&m---------------------------------------------------");
            BungeeUtil.sendMessage(sender, "  &6&lEpicGuard &8(BungeeCord version)");
            BungeeUtil.sendMessage(sender, "");
            BungeeUtil.sendMessage(sender, "&8» &7Running version &f" + this.plugin.getDescription().getVersion());
            BungeeUtil.sendMessage(sender, "&8» &7Created by &fiShift &8© 2020");
            BungeeUtil.sendMessage(sender, "");
            BungeeUtil.sendMessage(sender, "&8» &7/guard status &8- &7Toggle antibot actionbar.");
            BungeeUtil.sendMessage(sender, "&8» &7/guard reload &8- &7Reload configuration and messages.");
            BungeeUtil.sendMessage(sender, "&8» &7/guard whitelist <adress> &8- &7Add specific adress to the whitelist.");
            BungeeUtil.sendMessage(sender, "&8» &7/guard blacklist <adress> &8- &7Add specific adress to the blacklist.");
            BungeeUtil.sendMessage(sender, "");
            BungeeUtil.sendMessage(sender, "&8&m---------------------------------------------------");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "status":
                if (!(sender instanceof ProxiedPlayer)) {
                    sender.sendMessage(TextComponent.fromLegacyText("This command is only for players!"));
                    return;
                }

                final ProxiedPlayer player = (ProxiedPlayer) sender;
                if (BungeeNotify.getUsers().contains(player.getUniqueId())) {
                    BungeeNotify.getUsers().remove(player.getUniqueId());
                    BungeeUtil.sendMessage(sender, Messages.prefix + Messages.statusOff);
                    return;
                }

                BungeeNotify.getUsers().add(player.getUniqueId());
                BungeeUtil.sendMessage(sender, Messages.prefix + Messages.statusOn);
                break;
            case "reload":
                BungeeUtil.sendMessage(sender, Messages.prefix + Messages.configReload);
                Configuration.load();
                break;
            case "whitelist":
                if (args.length != 2) {
                    BungeeUtil.sendMessage(sender, Messages.prefix + Messages.usage.replace("{USAGE}", " guard whitelist <adress>"));
                    return;
                }

                final String address = args[1];
                this.manager.getStorageManager().getStorage().whitelist(address);
                BungeeUtil.sendMessage(sender, Messages.prefix + Messages.whitelisted.replace("{ADDRESS}", address));
                break;
            case "blacklist":
                if (args.length != 2) {
                    BungeeUtil.sendMessage(sender, Messages.prefix + Messages.usage.replace("{USAGE}", " guard blacklist <adress>"));
                    return;
                }

                final String address1 = args[1];
                this.manager.getStorageManager().getStorage().blacklist(address1);
                BungeeUtil.sendMessage(sender, Messages.prefix + Messages.blacklisted.replace("{ADDRESS}", address1));
                break;
            default:
                BungeeUtil.sendMessage(sender, Messages.prefix + Messages.unknownCommand);
        }
    }
}
