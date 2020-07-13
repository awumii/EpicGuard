package me.ishift.epicguard.bukkit.command;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.config.MessagesConfiguration;
import me.ishift.epicguard.core.user.User;
import me.ishift.epicguard.core.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EpicGuardCommand implements CommandExecutor {
    private final EpicGuard epicGuard;

    public EpicGuardCommand(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        MessagesConfiguration config = this.epicGuard.getMessages();
        String prefix = this.epicGuard.getMessages().prefix;
        if (args.length < 1) {
            send(sender, "&8&m---------------------------------------------------");
            send(sender, "  &6&lEpicGuard &8(Spigot version)");
            send(sender, "  &7Version: &f" + this.epicGuard.getVersion());
            send(sender, "");
            send(sender, " &8• &b/guard stats &7- show plugin statistics.");
            send(sender, " &8• &b/guard notifications &7- enable actionbar notifications.");
            send(sender, " &8• &b/guard reload &7- reload config and messages.");
            send(sender, " &8• &b/guard whitelist <address> &7- whitelist an address.");
            send(sender, " &8• &b/guard blacklist <address> &7- blacklist an address.");
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
                send(sender, "  &6&lEpicGuard &8- &7Statistics)");
                send(sender, "");
                send(sender, " &8• &7Blacklisted IPs: &6" + this.epicGuard.getStorageManager().getBlacklist().size());
                send(sender, " &8• &7Whitelisted IPs: &6" + this.epicGuard.getStorageManager().getWhitelist().size());
                send(sender, "");
                send(sender, " &8• &7Connections/s: &6" + this.epicGuard.getConnectionPerSecond());
                send(sender, "");
                send(sender, "&8&m---------------------------------------------------");
                break;
            case "notifications":
                User user = this.epicGuard.getUserManager().getUser(((Player) sender).getUniqueId());
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
                    return true;
                }
                send(sender, prefix + config.whitelisted.replace("{IP}", args[1]));
                this.epicGuard.getStorageManager().whitelist(args[1]);
                break;
            case "blacklist":
                if (args.length != 2) {
                    send(sender, prefix + config.usage.replace("{USAGE}", "/guard blacklist <address>"));
                    return true;
                }
                send(sender, prefix + config.blacklisted.replace("{IP}", args[1]));
                this.epicGuard.getStorageManager().blacklist(args[1]);
                break;
        }
        return true;
    }

    private void send(CommandSender sender, String message) {
        sender.sendMessage(ChatUtils.colored(message));
    }
}
