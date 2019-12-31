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
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&aYou are running EPICGUARD vBungee! (By iShift)")));
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&cCONNECTION/s -> &6" + BungeeAttack.getConnectionPerSecond())));
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&cPING/s -> &6" + BungeeAttack.getPingPerSecond())));
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&cATTACK -> &6" + BungeeAttack.isAttack())));
            return;
        }
        if (commandSender instanceof ProxiedPlayer) {
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&cOnly console!")));
            return;
        }
        if (strings[0].equalsIgnoreCase("display")) {
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&aToggled Display")));
            GuardBungee.display = !GuardBungee.display;
        }
        else if (strings[0].equalsIgnoreCase("log")) {
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&aToggled Log")));
            GuardBungee.log = !GuardBungee.log;
        }
        else {
            commandSender.sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&cCommand not found!")));
        }
    }
}
