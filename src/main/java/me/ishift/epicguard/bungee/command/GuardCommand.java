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
import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.Messages;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GuardCommand extends Command {
    public GuardCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1) {
            BungeeUtil.sendMessage(sender, "&8&m---------------------------------------------------");
            BungeeUtil.sendMessage(sender, "  &6&lEpicGuard");
            BungeeUtil.sendMessage(sender, "");
            BungeeUtil.sendMessage(sender, "  &7Running version &f" + EpicGuardBungee.getInstance().getDescription().getVersion());
            BungeeUtil.sendMessage(sender, "  &7Created by &fiShift &8© 2020");
            BungeeUtil.sendMessage(sender, "  &7Licensed under &8GPLv3 &7license.");
            BungeeUtil.sendMessage(sender, "");
            BungeeUtil.sendMessage(sender, " &7/guard status &8- &7Toggle antibot actionbar.");
            BungeeUtil.sendMessage(sender, " &7/guard reload &8- &7Reload configuration and messages.");
            BungeeUtil.sendMessage(sender, " &7/guard whitelist <adress> &8- &7Add specific adress to the whitelist.");
            BungeeUtil.sendMessage(sender, " &7/guard blacklist <adress> &8- &7Add specific adress to the blacklist.");
            BungeeUtil.sendMessage(sender, "");
            BungeeUtil.sendMessage(sender, "&8&m---------------------------------------------------");
            return;
        }

        if (sender instanceof ProxiedPlayer && !sender.getPermissions().contains("epicguard.admin")) {
            BungeeUtil.sendMessage(sender, Messages.prefix + Messages.noPermission);
            return;
        }

        if (args[0].equalsIgnoreCase("status")) {
            final ProxiedPlayer player = (ProxiedPlayer) sender;
            final boolean isEnabled = EpicGuardBungee.getInstance().getStatusPlayers().contains(player.getUniqueId());

            if (isEnabled) {
                EpicGuardBungee.getInstance().getStatusPlayers().remove(player.getUniqueId());
                BungeeUtil.sendMessage(sender, Messages.prefix + Messages.statusOff);
                return;
            }
            EpicGuardBungee.getInstance().getStatusPlayers().add(player.getUniqueId());
            BungeeUtil.sendMessage(sender, Messages.prefix + Messages.statusOn);
        } else if (args[0].equalsIgnoreCase("reload")) {
            BungeeUtil.sendMessage(sender, Messages.prefix + Messages.configReload);
            Configuration.load();
        } else if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length != 2) {
                BungeeUtil.sendMessage(sender, Messages.prefix + Messages.usage.replace("{USAGE}", " guard whitelist <adress>"));
                return;
            }
            final String address = args[1];
            StorageManager.getStorage().whitelist(address);
            BungeeUtil.sendMessage(sender, Messages.prefix + Messages.whitelisted.replace("{ADDRESS}", address));
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length != 2) {
                BungeeUtil.sendMessage(sender, Messages.prefix + Messages.usage.replace("{USAGE}", " guard blacklist <adress>"));
                return;
            }
            final String address = args[1];
            StorageManager.getStorage().blacklist(address);
            BungeeUtil.sendMessage(sender, Messages.prefix + Messages.blacklisted.replace("{ADDRESS}", address));
        } else {
            BungeeUtil.sendMessage(sender, Messages.prefix + Messages.unknownCommand);
        }
    }
}
