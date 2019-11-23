package pl.polskistevek.guard.bukkit.command;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.gui.GuiMain;
import pl.polskistevek.guard.bukkit.listener.PlayerJoinListener;
import pl.polskistevek.guard.bukkit.listener.ServerListPingListener;
import pl.polskistevek.guard.bukkit.util.MessagesBukkit;
import pl.polskistevek.guard.bungee.util.MessagesBungee;
import pl.polskistevek.guard.utils.GEO;
import pl.polskistevek.guard.bukkit.listener.PreLoginListener;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.bukkit.manager.UserManager;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.bukkit.util.Updater;
import java.io.IOException;
import java.util.Date;

public class GuardCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)){
            System.out.println("[EpicGuard] You are running EpicGuard v" + BukkitMain.getPlugin(BukkitMain.class).getDescription().getVersion());
            System.out.println("[EpicGuard] Use this command in-game.");
            return false;
        }
        Player p = (Player) sender;
        if (!p.hasPermission(BukkitMain.PERMISSION)){
            p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + MessagesBukkit.NO_PERMISSION));
            return false;
        }
        if (args.length > 0) {
            switch (args[0]) {
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
                    if (args.length == 2){
                        Player player = Bukkit.getPlayerExact(args[1]);
                        if (player == null){
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
                            p.sendMessage(ChatUtil.fix("&8▪ &7Country: &f" + GEO.dbReader.country(player.getAddress().getAddress()).getCountry().getIsoCode()));
                        } catch (IOException | GeoIp2Exception e) {
                            e.printStackTrace();
                        }
                        p.sendMessage(ChatUtil.fix("&8▪ &7OP: " + (player.isOp() ? "&a&lYES" : "&c&lNO")));
                        p.sendMessage(ChatUtil.fix(""));
                        p.sendMessage(ChatUtil.fix("&6[IP History]"));
                        for (String adress : UserManager.getUser(player).getAdresses()){
                            p.sendMessage(ChatUtil.fix(" &8- &c" + adress));
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
                    for (OfflinePlayer player : Bukkit.getOperators()){
                        Date currentDate = new Date(player.getLastPlayed());
                        p.sendMessage(ChatUtil.fix("&8▪ &f" + player.getName() + " &8[" + (player.isOnline() ? "&aONLINE" : "&cOFFLINE") + "&8], &7Last Online: &f" + currentDate));
                    }
                    p.sendMessage(ChatUtil.fix(""));
                    p.sendMessage(ChatUtil.fix("&7-----------------------------------------------"));
                    break;
                case "restart":
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "plugman unload epicguard");
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "plugman load epicguard");
                    break;
                case "status":
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + (BukkitMain.STATUS ? "&cToggled off" : "&aToggled on") + " &7bot notification status!"));
                    BukkitMain.STATUS = !BukkitMain.STATUS;
                    break;
                case "reload":
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Reloading config..."));
                    BukkitMain.loadConfig();
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&aSuccesfully &7reloaded config!"));
                    break;
                case "antibot":
                    p.sendMessage(ChatUtil.fix("&7------------------------------------------"));
                    p.sendMessage(ChatUtil.fix(""));
                    p.sendMessage(ChatUtil.fix("&6[AntiBot Status]"));
                    p.sendMessage(ChatUtil.fix("&8▪ &7Antibot modules: " + (BukkitMain.ANTIBOT ? "&aEnabled" : "&cDisabled")));
                    p.sendMessage(ChatUtil.fix("&8▪ &7Firewall: &f" + (BukkitMain.FIREWALL ? "&aEnabled" : "&cDisabled")));
                    p.sendMessage(ChatUtil.fix("&8▪ &7Attack Protection: " + (PreLoginListener.attack ? "&aActive" : "&cListening...")));
                    p.sendMessage(ChatUtil.fix("&8▪ &7Connections Per Second: &f" + PreLoginListener.cps));
                    p.sendMessage(ChatUtil.fix("&8▪ &7Pings Per Second: &f" + ServerListPingListener.cps_ping));
                    p.sendMessage(ChatUtil.fix("&8▪ &7Joins Per Second: &f" + PlayerJoinListener.jps));
                    p.sendMessage(ChatUtil.fix(""));
                    p.sendMessage(ChatUtil.fix("&6[BlacklistManager Statistics]"));
                    p.sendMessage(ChatUtil.fix("&8▪ &7Blacklisted IPs: &f" + BlacklistManager.IP_BL.size()));
                    p.sendMessage(ChatUtil.fix("&8▪ &7Whitelisted IPs: &f" + BlacklistManager.IP_WL.size()));
                    p.sendMessage(ChatUtil.fix(""));
                    p.sendMessage(ChatUtil.fix("&7------------------------------------------"));
                    break;
                default:
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7Unknown command! &7Use &f/guard&7."));
            }
            return false;
        }
        p.sendMessage(ChatUtil.fix("&7------------------------------------------"));
        p.sendMessage(ChatUtil.fix(""));
        p.sendMessage(ChatUtil.fix("&6[Basic Information]"));
        p.sendMessage(ChatUtil.fix("&8▪ &7Author: &fPolskiStevek"));
        p.sendMessage(ChatUtil.fix("&8▪ &7Version: &f" + Updater.currentVersion + (Updater.updateAvaible ? " &c&lOUTDATED &8(&7Latest: " + Updater.lastestVersion + "&8)" : " &a&lLATEST")));
        p.sendMessage(ChatUtil.fix("&8▪ &7Bukkit Version: &f" + Bukkit.getServer().getBukkitVersion()));
        p.sendMessage(ChatUtil.fix(""));
        p.sendMessage(ChatUtil.fix("&6[Plugin Commands]"));
        p.sendMessage(ChatUtil.fix("&8▪ &7/guard menu &8- &fmain plugin GUI."));
        p.sendMessage(ChatUtil.fix("&8▪ &7/guard reload &8- &freload plugin config."));
        p.sendMessage(ChatUtil.fix("&8▪ &7/guard antibot &8- &fsee antibot status."));
        p.sendMessage(ChatUtil.fix("&8▪ &7/guard op &8- &flist of opped players."));
        p.sendMessage(ChatUtil.fix("&8▪ &7/guard status &8- &ftoggle title and actionbar."));
        p.sendMessage(ChatUtil.fix("&8▪ &7/guard player <nick> &8- &fcheck basic info about player."));
        p.sendMessage(ChatUtil.fix("&8▪ &7/guard whitelist <adress> &8- &fadd adress to whitelist and remove from blacklist."));
        p.sendMessage(ChatUtil.fix("&8▪ &7/guard blacklist <adress> &8- &fadd adress to blacklist."));
        p.sendMessage(ChatUtil.fix(""));
        p.sendMessage(ChatUtil.fix("&7------------------------------------------"));
        Updater.notify(p);
        return true;
    }
}
