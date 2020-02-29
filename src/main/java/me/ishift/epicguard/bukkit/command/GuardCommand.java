package me.ishift.epicguard.bukkit.command;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.gui.GuiMain;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.bukkit.util.server.Updater;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.GeoAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuardCommand implements CommandExecutor {
    public static void send(CommandSender sender, String message) {
        sender.sendMessage(ChatUtil.fix(Messages.prefix + message));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command c, String s, String[] args) {
        if (sender instanceof Player && !sender.hasPermission(GuardBukkit.PERMISSION)) {
            send(sender, "&7This server uses &6EpicGuard v" + Updater.getCurrentVersion() + " &7by &ciShift&7.");
            send(sender, Messages.noPermission);
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatUtil.fix("&8*---------------------------------------------------*"));
            sender.sendMessage(ChatUtil.fix("   &6&lEpicGuard"));
            sender.sendMessage(ChatUtil.fix(""));
            sender.sendMessage(ChatUtil.fix("   &7Running version &f" + Updater.getCurrentVersion()));
            sender.sendMessage(ChatUtil.fix("   &7Created by &fiShift"));
            sender.sendMessage(ChatUtil.fix(""));
            sender.sendMessage(ChatUtil.fix(" &f/" + s + " menu &8- &7Open main plugin GUI."));
            sender.sendMessage(ChatUtil.fix(" &f/" + s + " status &8- &7Toggle antibot notifications (titles)."));
            sender.sendMessage(ChatUtil.fix(" &f/" + s + " reload &8- &7Reload configuration and messages."));
            sender.sendMessage(ChatUtil.fix(" &f/" + s + " reset &8- &7Disabling attack mode and resetting counters."));
            sender.sendMessage(ChatUtil.fix(" &f/" + s + " player <player> &8- &7See information about specific player."));
            sender.sendMessage(ChatUtil.fix(" &f/" + s + " whitelist <adress> &8- &7Add specific adress to the whitelist."));
            sender.sendMessage(ChatUtil.fix(" &f/" + s + " blacklist <adress> &8- &7Add specific adress to the blacklist."));
            sender.sendMessage(ChatUtil.fix("&8*---------------------------------------------------*"));
            return true;
        }

        if (args[0].equalsIgnoreCase("menu")) {
            if (!(sender instanceof Player)) {
                send(sender, "&cThis command is player only.");
                return true;
            }
            GuiMain.show((Player) sender);
        } else if (args[0].equalsIgnoreCase("status")) {
            if (!(sender instanceof Player)) {
                send(sender, "&cThis command is player only.");
                return true;
            }
            final User user = UserManager.getUser((Player) sender);
            send(sender, (user.isNotifications() ? "&cToggled off" : "&aToggled on") + " &7bot notification status!");
            user.setNotifications(!user.isNotifications());
        } else if (args[0].equalsIgnoreCase("reload")) {
            send(sender, "&7Reloading config...");
            GuardBukkit.getInstance().reloadConfig();
            Config.loadBukkit();
            Messages.load();
            send(sender, "&7Reloaded config &asuccesfully&7!");
        } else if (args[0].equalsIgnoreCase("player")) {
            if (args.length != 2) {
                send(sender, "&7Correct usage: &f/" + s + " player <player>");
                return true;
            }
            final Player player = Bukkit.getPlayer(args[1]);
            if (player == null) {
                send(sender, "&7Player &c" + args[1] + " &7not found!");
                return true;
            }
            send(sender, "&7Name: &f" + player.getName());
            send(sender, "&7UUID: &f" + player.getUniqueId());
            send(sender, "&7Country: &f" + GeoAPI.getCountryCode(player.getAddress().getAddress().getHostAddress()));
            send(sender, "&7Host Adress: &f" + player.getAddress().getAddress().getHostName());
            send(sender, "&7OP: " + (player.isOp() ? "&a&lYES" : "&c&lNO"));
            if (Config.ipHistoryEnable) {
                send(sender, "&6[IP History]");
                UserManager.getUser(player).getAddresses().forEach(address -> send(sender, " &7- &f" + address));
            }
        } else if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length != 2) {
                send(sender, "&7Correct usage: &f/" + s + " whitelist <adress>");
                return true;
            }
            StorageManager.whitelist(args[1]);
            send(sender, "&7Whitelisted IP: " + args[1]);
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length != 2) {
                send(sender, "&7Correct usage: &f/" + s + " blacklist <adress>");
                return true;
            }
            StorageManager.blacklist(args[1]);
            send(sender, "&7Blacklisted IP: " + args[1]);
        } else if (args[0].equalsIgnoreCase("reset")) {
            SpeedCheck.reset();
            SpeedCheck.setConnectPerSecond(0);
            SpeedCheck.setPingPerSecond(0);
            send(sender, "&7Attack counters has been &ccleared&7.");
        } else {
            send(sender, "&cCommand not found! Use &6/" + s);
        }
        return true;
    }
}
