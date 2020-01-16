package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.KickReason;
import me.ishift.epicguard.universal.util.Logger;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;

public class ConnectionCloser {
    public static void close(PendingConnection connection, KickReason reason) {
        if (GuardBungee.log) {
            Logger.info("Closing: " + connection.getAddress().getAddress().getHostAddress() + "(" + connection.getName() + "), (" + reason + ")]");
        }
        if (GuardBungee.status) {
            if (BungeeAttack.getConnectionPerSecond() > 5) {
                GuardBungee.plugin.getProxy().getPlayers().forEach(player -> {
                    if (player.getPermissions().contains("epicguard.admin")) {
                        BungeeUtil.sendTitle(player, MessagesBungee.ATTACK_TITLE.replace("{CPS}", String.valueOf(BungeeAttack.getConnectionPerSecond())), MessagesBungee.ATTACK_SUBTITLE.replace("{MAX}", String.valueOf(BungeeAttack.blockedBots)));
                        BungeeUtil.sendActionBar(player, MessagesBungee.ACTIONBAR_ATTACK.replace("{NICK}", connection.getName()).replace("{IP}", connection.getAddress().getAddress().getHostAddress()).replace("{DETECTION}", String.valueOf(reason)));
                    }
                });
            }
        }
        BungeeAttack.blockedBots++;
        if (reason == KickReason.GEO) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.MESSAGE_KICK_COUNTRY) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }

        if (reason == KickReason.ATTACK) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.MESSAGE_KICK_ATTACK) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }

        if (reason == KickReason.PROXY) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.MESSAGE_KICK_PROXY) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }

        if (reason == KickReason.BLACKLIST) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.MESSAGE_KICK_BLACKLIST) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }

        if (reason == KickReason.NAMECONTAINS) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.MESSAGE_KICK_NAMECONTAINS) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }
    }
}
