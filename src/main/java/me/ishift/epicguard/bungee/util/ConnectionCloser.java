package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.types.KickReason;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.Logger;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;

import java.util.stream.Collectors;

public class ConnectionCloser {
    public static void close(PendingConnection connection, KickReason reason) {
        if (GuardBungee.log) {
            Logger.info("Closing: " + connection.getVirtualHost().getAddress().getHostAddress() + "(" + connection.getName() + "), (" + reason + ")]");
        }

        if (GuardBungee.status) {
            if (BungeeAttack.getConnectionPerSecond() > 5) {
                GuardBungee.getInstance().getProxy().getPlayers().forEach(player -> {
                    if (player.getPermissions().contains("epicguard.admin")) {
                        BungeeUtil.sendTitle(player, MessagesBungee.attackTitle.replace("{CPS}", String.valueOf(BungeeAttack.getConnectionPerSecond())), MessagesBungee.attackSubtitle.replace("{MAX}", String.valueOf(BungeeAttack.blockedBots)));
                        BungeeUtil.sendActionBar(player, MessagesBungee.attackActionBar.replace("{NICK}", connection.getName()).replace("{IP}", connection.getVirtualHost().getAddress().getHostAddress()).replace("{DETECTION}", String.valueOf(reason)));
                    }
                });
            }
        }

        BungeeAttack.blockedBots++;
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
