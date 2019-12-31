package me.ishift.epicguard.bukkit.command;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.gui.GuiMain;
import me.ishift.epicguard.bukkit.manager.BlacklistManager;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.bukkit.manager.UserManager;
import me.ishift.epicguard.bukkit.object.User;
import me.ishift.epicguard.bukkit.util.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Date;

public class GuardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            Logger.info("You are running EpicGuard v" + GuardBukkit.getPlugin(GuardBukkit.class).getDescription().getVersion());
            Logger.info("Use this command in-game.");
            return false;
        }
        Player p = (Player) sender;
        if (!p.hasPermission(GuardBukkit.PERMISSION)) {
            p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + MessagesBukkit.NO_PERMISSION));
            return false;
        }
        if (args.length > 0) {
            final User user = UserManager.getUser(p);
            switch (args[0]) {
                case "help":
                    p.sendMessage(ChatUtil.fix(""));
                    p.sendMessage(ChatUtil.fix("&8▪ &c/guard menu &8- &7main plugin GUI."));
                    p.sendMessage(ChatUtil.fix("&8▪ &c/guard reload &8- &7reload plugin config."));
                    p.sendMessage(ChatUtil.fix("&8▪ &c/guard save &8- &7save data (history, blacklist etc)."));
                    p.sendMessage(ChatUtil.fix("&8▪ &c/guard op &8- &7list of opped players."));
                    p.sendMessage(ChatUtil.fix("&8▪ &c/guard status &8- &7toggle title and actionbar."));
                    p.sendMessage(ChatUtil.fix("&8▪ &c/guard player <nick> &8- &7check basic info about player."));
                    p.sendMessage(ChatUtil.fix("&8▪ &c/guard whitelist <adress> &8- &7add adress to whitelist and remove from blacklist."));
                    p.sendMessage(ChatUtil.fix("&8▪ &c/guard blacklist <adress> &8- &7add adress to blacklist."));
                    p.sendMessage(ChatUtil.fix(""));
                    break;
                case "menu":
                    GuiMain.show(p);
                    break;
                case "save":
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Succesfully saved data file."));
                    DataFileManager.save();
                    break;
                case "whitelist":
                    if (args.length == 2) {
                        BlacklistManager.addWhitelist(args[1]);
                        p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Succesfully whitelisted IP: &a" + args[1]));
                    }
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Correct usage: &f/guard whitelist <adress>"));
                    break;
                case "blacklist":
                    if (args.length == 2) {
                        BlacklistManager.add(args[1]);
                        p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Succesfully blacklisted IP: &c" + args[1]));
                    }
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Correct usage: &f/guard blacklist <adress>"));
                    break;
                case "player":
                    if (args.length == 2) {
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null) {
                            p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7The player &f" + args[1] + " &7is &coffline&7!"));
                            return false;
                        }
                        p.sendMessage(ChatUtil.fix("&7------------------------------------------"));
                        p.sendMessage(ChatUtil.fix(""));
                        p.sendMessage(ChatUtil.fix("&6[Basic Information]"));
                        p.sendMessage(ChatUtil.fix("&8▪ &7Name: &f" + player.getName()));
                        p.sendMessage(ChatUtil.fix("&8▪ &7UUID: &f" + player.getUniqueId()));
                        p.sendMessage(ChatUtil.fix("&8▪ &7First Join: &f" + new Date(player.getFirstPlayed())));
                        try {
                            p.sendMessage(ChatUtil.fix("&8▪ &7Country: &f" + GeoAPI.getDatabase().country(player.getAddress().getAddress()).getCountry().getIsoCode()));
                        } catch (IOException | GeoIp2Exception e) {
                            e.printStackTrace();
                        }
                        p.sendMessage(ChatUtil.fix("&8▪ &7OP: " + (player.isOp() ? "&a&lYES" : "&c&lNO")));
                        if (Config.IP_HISTORY_ENABLE) {
                            p.sendMessage(ChatUtil.fix(""));
                            p.sendMessage(ChatUtil.fix("&6[IP History]"));
                            for (String adress : UserManager.getUser(player).getAdresses()) {
                                p.sendMessage(ChatUtil.fix(" &8- &c" + adress));
                            }
                        }
                        p.sendMessage(ChatUtil.fix(""));
                        p.sendMessage(ChatUtil.fix("&7------------------------------------------"));
                        return false;
                    }
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Correct usage: &f/guard player <nick>"));
                    break;
                case "op":
                    p.sendMessage(ChatUtil.fix("&7-----------------------------------------------"));
                    p.sendMessage(ChatUtil.fix(""));
                    p.sendMessage(ChatUtil.fix("&6[Operator List]"));
                    for (OfflinePlayer player : Bukkit.getOperators()) {
                        Date currentDate = new Date(player.getLastPlayed());
                        p.sendMessage(ChatUtil.fix("&8▪ &f" + player.getName() + " &8[" + (player.isOnline() ? "&aONLINE" : "&cOFFLINE") + "&8], &7Last Online: &f" + currentDate));
                    }
                    p.sendMessage(ChatUtil.fix(""));
                    p.sendMessage(ChatUtil.fix("&7-----------------------------------------------"));
                    break;
                case "status":
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + (user.isNotifications() ? "&cToggled off" : "&aToggled on") + " &7bot notification status!"));
                    user.setNotifications(!user.isNotifications());
                    break;
                case "reload":
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Reloading config..."));
                    GuardBukkit.loadConfig();
                    MessagesBukkit.load();
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&aSuccesfully &7reloaded config!"));
                    break;
                default:
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Unknown command! &7Use &f/guard&7."));
            }
            return false;
        }
        GuiMain.show(p);
        Updater.notify(p);
        return true;
    }
}
