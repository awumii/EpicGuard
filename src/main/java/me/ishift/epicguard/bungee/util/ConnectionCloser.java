package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.types.KickReason;
import me.ishift.epicguard.universal.util.ChatUtil;
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
                GuardBungee.getInstance().getProxy().getPlayers().forEach(player -> {
                    if (player.getPermissions().contains("epicguard.admin")) {
                        BungeeUtil.sendTitle(player, MessagesBungee.attackTitle.replace("{CPS}", String.valueOf(BungeeAttack.getConnectionPerSecond())), MessagesBungee.attackSubtitle.replace("{MAX}", String.valueOf(BungeeAttack.blockedBots)));
                        BungeeUtil.sendActionBar(player, MessagesBungee.attackActionBar.replace("{NICK}", connection.getName()).replace("{IP}", connection.getAddress().getAddress().getHostAddress()).replace("{DETECTION}", String.valueOf(reason)));
                    }
                });
            }
        }
        BungeeAttack.blockedBots++;
        if (reason == KickReason.GEO) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.messageKickCountry) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }

        if (reason == KickReason.ATTACK) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.messageKickAttack) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }

        if (reason == KickReason.PROXY) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.messageKickProxy) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }

        if (reason == KickReason.BLACKLIST) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.messageKickBlacklist) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }

        if (reason == KickReason.NAMECONTAINS) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBungee.messageKickNamecontains) {
                sb.append(s).append("\n");
            }
            connection.disconnect(new TextComponent(ChatUtil.fix(sb.toString())));
        }
    }
}
