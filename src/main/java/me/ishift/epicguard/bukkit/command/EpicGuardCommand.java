package me.ishift.epicguard.bukkit.command;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.util.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EpicGuardCommand implements CommandExecutor {
    private final EpicGuard epicGuard;

    public EpicGuardCommand(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 1) {
            send(sender, "&7Currently running &cEpicGuard v" + this.epicGuard.getVersion());
            send(sender, "&6/" + s + " stats &7- show plugin statistics.");
            send(sender, "&6/" + s + " reload &7- reload config and messages.");
            send(sender, "&6/" + s + " reload &7- reload config and messages.");
            send(sender, "&6/" + s + " whitelist <address> &7- whitelist an address.");
            send(sender, "&6/" + s + " blacklist <address> &7- blacklist an address.");
            return true;
        }

        if (!sender.hasPermission("epicguard.admin")) {
            send(sender, this.epicGuard.getMessages().noPermission);
            return true;
        }

        switch (args[0]) {
            case "reload":
                send(sender, this.epicGuard.getMessages().reload);
                this.epicGuard.reloadConfig();
                break;
            case "whitelist":
                if (args.length != 2) {
                    send(sender, this.epicGuard.getMessages().usage.replace("{USAGE}", "/guard whitelist <address>"));
                    return true;
                }
                send(sender, this.epicGuard.getMessages().whitelisted.replace("{IP}", args[1]));
                this.epicGuard.getStorageManager().whitelist(args[1]);
                break;
            case "blacklist":
                if (args.length != 2) {
                    send(sender, this.epicGuard.getMessages().usage.replace("{USAGE}", "/guard blacklist <address>"));
                    return true;
                }
                send(sender, this.epicGuard.getMessages().blacklisted.replace("{IP}", args[1]));
                this.epicGuard.getStorageManager().blacklist(args[1]);
                break;
        }
        return true;
    }

    private void send(CommandSender sender, String message) {
        sender.sendMessage(ChatUtils.colored(this.epicGuard.getMessages().prefix + message));
    }
}
