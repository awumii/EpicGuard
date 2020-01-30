package me.ishift.epicguard.bungee.command;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.bungee.util.MessagesBungee;
import me.ishift.epicguard.universal.util.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class GuardCommand extends Command {
    public GuardCommand(String name) {
        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (strings.length < 1) {
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.prefix + "&aYou are running EPICGUARD vBungee! (By iShift and ruzekh)")));
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.prefix + "&cCONNECTION/s -> &6" + BungeeAttack.getConnectionPerSecond())));
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.prefix + "&cPING/s -> &6" + BungeeAttack.getPingPerSecond())));
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.prefix + "&cATTACK -> &6" + BungeeAttack.isAttack())));
            return;
        }
        if (commandSender instanceof ProxiedPlayer && !commandSender.getPermissions().contains("epicguard.admin")) {
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.prefix + "&cYou don't have permission to do this &8[&6use command /guard&8]")));
            return;
        }
        if (strings[0].equalsIgnoreCase("log")) {
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.prefix + "&aToggled Log")));
            GuardBungee.log = !GuardBungee.log;
        } else if (strings[0].equalsIgnoreCase("notifications")) {
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.prefix + "&aToggled title notifications")));
            GuardBungee.status = !GuardBungee.status;
        } else {
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.prefix + "&cCommand not found!")));
        }
    }
}
