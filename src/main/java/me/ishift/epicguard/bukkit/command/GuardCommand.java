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

package me.ishift.epicguard.bukkit.command;

import me.ishift.epicguard.api.EpicGuardAPI;
import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.common.AttackSpeed;
import me.ishift.epicguard.common.Config;
import me.ishift.epicguard.common.Messages;
import me.ishift.epicguard.common.StorageManager;
import me.ishift.epicguard.api.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GuardCommand implements CommandExecutor {
    public static void send(CommandSender sender, String message) {
        sender.sendMessage(ChatUtil.fix(Messages.prefix + message));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command c, @NotNull String s, @NotNull String[] args) {
        if (sender instanceof Player && !sender.hasPermission("epicguard.admin")) {
            send(sender, "&7This server uses &6EpicGuard v" + Updater.getCurrentVersion() + " &7by &ciShift&7.");
            send(sender, Messages.noPermission);
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatUtil.fix("&8&m---------------------------------------------------"));
            sender.sendMessage(ChatUtil.fix("  &6&lEpicGuard"));
            sender.sendMessage(ChatUtil.fix(""));
            sender.sendMessage(ChatUtil.fix("  &7Running version &f" + Updater.getCurrentVersion()));
            sender.sendMessage(ChatUtil.fix("  &7Created by &fiShift &8© 2020"));
            sender.sendMessage(ChatUtil.fix("  &7Licensed under &8GPLv3 &7license."));
            sender.sendMessage(ChatUtil.fix(""));
            sender.sendMessage(ChatUtil.fix(" &7/guard &fmenu &7to open main plugin GUI."));
            sender.sendMessage(ChatUtil.fix(" &7/guard &fstatus &7to toggle antibot notifications."));
            sender.sendMessage(ChatUtil.fix(" &7/guard &freload &7to reload configuration and messages."));
            sender.sendMessage(ChatUtil.fix(" &7/guard &freset &7to disable attack mode and reset counters."));
            sender.sendMessage(ChatUtil.fix(" &7/guard &fplayer <player> &7to see information about specific player."));
            sender.sendMessage(ChatUtil.fix(" &7/guard &fwhitelist <adress> &7to add specific adress to the whitelist."));
            sender.sendMessage(ChatUtil.fix(" &7/guard &fblacklist <adress> &7to add specific adress to the blacklist."));
            sender.sendMessage(ChatUtil.fix("&8&m---------------------------------------------------"));
            return true;
        }

        if (args[0].equalsIgnoreCase("menu")) {
            if (!(sender instanceof Player)) {
                send(sender, Messages.playerOnly);
                return true;
            }
            GuardGui.showMain((Player) sender);
        }

        else if (args[0].equalsIgnoreCase("status")) {
            if (!(sender instanceof Player)) {
                send(sender, Messages.playerOnly);
                return true;
            }
            final User user = UserManager.getUser((Player) sender);
            send(sender, (user.isNotifications() ? Messages.statusOff : Messages.statusOn));
            user.setNotifications(!user.isNotifications());
        }

        else if (args[0].equalsIgnoreCase("reload")) {
            GuardBukkit.getInstance().reloadConfig();
            Config.loadBukkit();
            Messages.load();
            send(sender, Messages.configReload);
        }

        else if (args[0].equalsIgnoreCase("player")) {
            if (args.length != 2) {
                send(sender, Messages.usage.replace("{USAGE}", s + " player <player>"));
                return true;
            }
            final Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                send(sender, Messages.playerNotFound);
                return true;
            }
            send(sender, "&7Name: &f" + player.getName());
            send(sender, "&7UUID: &f" + player.getUniqueId());
            send(sender, "&7Country: &f" + EpicGuardAPI.getGeoApi().getCountryCode(player.getAddress().getAddress().getHostAddress()));
            send(sender, "&7City: &f" + EpicGuardAPI.getGeoApi().getCity(player.getAddress().getAddress().getHostAddress()));
            send(sender, "&7Host Adress: &f" + player.getAddress().getAddress().getHostName());
            send(sender, "&7OP: " + (player.isOp() ? "&a&lYES" : "&c&lNO"));
            if (Config.ipHistoryEnable) {
                send(sender, "&6[IP History]");
                UserManager.getUser(player).getAddresses().forEach(address -> send(sender, " &7- &f" + address));
            }
        }

        else if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length != 2) {
                send(sender, Messages.usage.replace("{USAGE}", s + " whitelist <address>"));
                return true;
            }
            final String address = args[1];
            StorageManager.whitelist(address);
            send(sender, Messages.whitelisted.replace("{ADDRESS}", address));
        }

        else if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length != 2) {
                send(sender, Messages.usage.replace("{USAGE}", s + " blacklist <address>"));
                return true;
            }
            final String address = args[1];
            StorageManager.blacklist(address);
            send(sender, Messages.blacklisted.replace("{ADDRESS}", address));
        }

        else if (args[0].equalsIgnoreCase("reset")) {
            AttackSpeed.reset();
            AttackSpeed.setConnectPerSecond(0);
            AttackSpeed.setPingPerSecond(0);
            send(sender, Messages.reset);

        } else {
            send(sender, Messages.unknownCommand);
        }
        return true;
    }
}
