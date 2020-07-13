package me.ishift.epicguard.bungee.command;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.config.MessagesConfiguration;
import me.ishift.epicguard.core.user.User;
import me.ishift.epicguard.core.util.ChatUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

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
            return;
        }

        switch (args[0]) {
            case "stats":
                send(sender, "&8&m---------------------------------------------------");
                send(sender, "  &6&lEpicGuard &8- &7Statistics)");
                send(sender, "");
                send(sender, prefix + "&7Blacklisted IPs: &6" + this.epicGuard.getStorageManager().getBlacklist().size());
                send(sender, prefix + "&7Whitelisted IPs: &6" + this.epicGuard.getStorageManager().getWhitelist().size());
                send(sender, "");
                send(sender, " &8• &7Connections/s: &6" + this.epicGuard.getConnectionPerSecond());
                send(sender, "");
                send(sender, "&8&m---------------------------------------------------");
                break;
            case "notifications":
                ProxiedPlayer player = (ProxiedPlayer) sender;
                User user = this.epicGuard.getUserManager().getUser((player.getUniqueId()));
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
