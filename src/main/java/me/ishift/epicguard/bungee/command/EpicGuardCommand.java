package me.ishift.epicguard.bungee.command;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.config.MessagesConfiguration;
import me.ishift.epicguard.core.user.User;
import me.ishift.epicguard.core.util.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import org.bukkit.entity.Player;

public class EpicGuardCommand extends Command {
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
            send(sender, "&8&m----------(&4&l EPICGUARD &8&m)----------");
            send(sender, "&7Current version: &c" + this.epicGuard.getVersion());
            send(sender, "");
            send(sender, "&6/guard stats &7- show plugin statistics.");
            send(sender, "&6/guard notifications &7- enable actionbar notifications.");
            send(sender, "&6/guard reload &7- reload config and messages.");
            send(sender, "&6/guard whitelist <address> &7- whitelist an address.");
            send(sender, "&6/guard blacklist <address> &7- blacklist an address.");
            send(sender, "&8&m--------------------------------------");
            return;
        }

        if (!sender.hasPermission("epicguard.admin")) {
            send(sender, prefix + config.noPermission);
            return;
        }

        switch (args[0]) {
            case "stats":
                send(sender, prefix + "&7Blacklisted addresses: &6" + this.epicGuard.getStorageManager().getBlacklist().size());
                send(sender, prefix + "&7Whitelisted addresses: &6" + this.epicGuard.getStorageManager().getWhitelist().size());
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
        }
    }

    private void send(CommandSender sender, String message) {
        sender.sendMessage(TextComponent.fromLegacyText(ChatUtils.colored(message)));
    }
}
