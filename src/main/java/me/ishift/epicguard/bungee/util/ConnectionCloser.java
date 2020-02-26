package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import me.ishift.epicguard.universal.types.Reason;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.Logger;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;

import java.util.stream.Collectors;

public class ConnectionCloser {
    public static void close(PendingConnection connection, Reason reason) {
        if (GuardBungee.log) {
            Logger.info("Closing: " + connection.getVirtualHost().getAddress().getHostAddress() + "(" + connection.getName() + "), (" + reason + ")]");
        }

        SpeedCheck.setTotalBots(SpeedCheck.getTotalBots() + 1);
        if (reason == Reason.GEO) {
            connection.disconnect(new TextComponent(ChatUtil.fix(Messages.messageKickCountry.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }

        if (reason == Reason.ATTACK) {
            connection.disconnect(new TextComponent(ChatUtil.fix(Messages.messageKickAttack.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }

        if (reason == Reason.PROXY) {
            connection.disconnect(new TextComponent(ChatUtil.fix(Messages.messageKickProxy.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }

        if (reason == Reason.BLACKLIST) {
            connection.disconnect(new TextComponent(ChatUtil.fix(Messages.messageKickBlacklist.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }

        if (reason == Reason.NAMECONTAINS) {
            connection.disconnect(new TextComponent(ChatUtil.fix(Messages.messageKickNamecontains.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }
    }
}
