package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.bungee.util.MessagesBungee;
import me.ishift.epicguard.universal.util.ChatUtil;
import net.md_5.bungee.api.chat.TextComponent;

public class DisplayTask implements Runnable {
    @Override
    public void run() {
        if (BungeeAttack.getPingPerSecond() < 0) {
            BungeeAttack.pingPerSecond = 0;
        }
        if (BungeeAttack.getConnectionPerSecond() < 0) {
            BungeeAttack.connectionPerSecond = 0;
        }
        if (GuardBungee.display) {
            GuardBungee.plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&rCONNECTION/s -> &4" + BungeeAttack.getConnectionPerSecond())));
            GuardBungee.plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&rPING/s -> &4" + BungeeAttack.getPingPerSecond())));
            GuardBungee.plugin.getProxy().getConsole().sendMessage(new TextComponent(ChatUtil.fix(MessagesBungee.PREFIX + "&rATTACK -> &4" + BungeeAttack.isAttack())));
            GuardBungee.plugin.getProxy().getConsole().sendMessage(new TextComponent(""));
            GuardBungee.plugin.getProxy().getConsole().sendMessage(new TextComponent(""));
            GuardBungee.plugin.getProxy().getConsole().sendMessage(new TextComponent(""));
        }
    }
}
