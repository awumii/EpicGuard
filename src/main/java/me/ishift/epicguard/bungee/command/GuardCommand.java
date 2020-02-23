package me.ishift.epicguard.bungee.command;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.bungee.util.MessagesBungee;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;
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
            BungeeUtil.sendMessage(sender, "&8&m-----------------------------------------------------");
            BungeeUtil.sendMessage(sender, "   &6&lEPICGUARD &fv" + GuardBungee.getInstance().getDescription().getVersion());
            BungeeUtil.sendMessage(sender, "   &7&oMain management command");
            BungeeUtil.sendMessage(sender, "");
            BungeeUtil.sendMessage(sender, " &7Current CPS: &c" + BungeeAttack.getConnectionPerSecond());
            BungeeUtil.sendMessage(sender, " &7Current PPS: &c" + BungeeAttack.getPingPerSecond());
            BungeeUtil.sendMessage(sender, "");
            BungeeUtil.sendMessage(sender, " &6/guard status &8- &7Toggle antibot actionbar.");
            BungeeUtil.sendMessage(sender, " &6/guard log &8- &7Toggle advanced console logging.");
            BungeeUtil.sendMessage(sender, " &6/guard reload &8- &7Reload configuration and messages.");
            BungeeUtil.sendMessage(sender, " &6/guard whitelist <adress> &8- &7Add specific adress to the whitelist.");
            BungeeUtil.sendMessage(sender, " &6/guard blacklist <adress> &8- &7Add specific adress to the blacklist.");
            BungeeUtil.sendMessage(sender, "");
            BungeeUtil.sendMessage(sender, "&8&m-----------------------------------------------------");
            return;
        }

        if (sender instanceof ProxiedPlayer && !sender.getPermissions().contains("epicguard.admin")) {
            BungeeUtil.sendMessage(sender, MessagesBungee.prefix + "&cYou don't have permission to do this &8[&6use command /guard&8]");
            return;
        }

        if (args[0].equalsIgnoreCase("log")) {
            BungeeUtil.sendMessage(sender, MessagesBungee.prefix + "&aToggled console logging");
            GuardBungee.log = !GuardBungee.log;
        } else if (args[0].equalsIgnoreCase("status")) {
            BungeeUtil.sendMessage(sender, MessagesBungee.prefix + "&7Toggled actionbar");
            GuardBungee.status = !GuardBungee.status;
        } else if (args[0].equalsIgnoreCase("reload")) {
            BungeeUtil.sendMessage(sender, MessagesBungee.prefix + "&aReloaded configuration.");
            Config.loadBungee();
        } else if (args[0].equalsIgnoreCase("whitelist")) {
            if (args.length != 2) {
                BungeeUtil.sendMessage(sender, MessagesBungee.prefix + "&7Correct usage: &f/guard whitelist <adress>");
                return;
            }
            StorageManager.whitelist(args[1]);
            BungeeUtil.sendMessage(sender, MessagesBungee.prefix + "&7Whitelisted IP: " + args[1]);
        } else if (args[0].equalsIgnoreCase("blacklist")) {
            if (args.length != 2) {
                BungeeUtil.sendMessage(sender, MessagesBungee.prefix + "&7Correct usage: &f/guard blacklist <adress>");
                return;
            }
            StorageManager.blacklist(args[1]);
            BungeeUtil.sendMessage(sender, MessagesBungee.prefix + "&7Blacklisted IP: " + args[1]);
        } else {
            BungeeUtil.sendMessage(sender, MessagesBungee.prefix + "&cCommand not found!");
        }
    }
}
