package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.types.KickReason;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.Logger;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;

import java.util.stream.Collectors;

public class ConnectionCloser {
    public static void close(PendingConnection connection, KickReason reason) {
        if (GuardBungee.log) {
            Logger.info("Closing: " + connection.getVirtualHost().getAddress().getHostAddress() + "(" + connection.getName() + "), (" + reason + ")]");
        }

        BungeeAttack.setBlockedBots(BungeeAttack.getBlockedBots() + 1);
        if (reason == KickReason.GEO) {
            connection.disconnect(new TextComponent(ChatUtil.fix(MessagesBungee.messageKickCountry.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }

        if (reason == KickReason.ATTACK) {
            connection.disconnect(new TextComponent(ChatUtil.fix(MessagesBungee.messageKickAttack.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }

        if (reason == KickReason.PROXY) {
            connection.disconnect(new TextComponent(ChatUtil.fix(MessagesBungee.messageKickProxy.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }

        if (reason == KickReason.BLACKLIST) {
            connection.disconnect(new TextComponent(ChatUtil.fix(MessagesBungee.messageKickBlacklist.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }

        if (reason == KickReason.NAMECONTAINS) {
            connection.disconnect(new TextComponent(ChatUtil.fix(MessagesBungee.messageKickNamecontains.stream().map(s -> s + "\n").collect(Collectors.joining()))));
        }
    }
}
