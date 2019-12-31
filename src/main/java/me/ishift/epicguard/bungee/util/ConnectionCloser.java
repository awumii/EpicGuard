package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.KickReason;
import me.ishift.epicguard.universal.util.Logger;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;

public class ConnectionCloser {
    public static void close(PendingConnection connection, KickReason reason) {
        if (GuardBungee.log) {
            Logger.info("Closing: " + connection.getAddress().getAddress().getHostAddress() + "(" + connection.getName() + "), (" + reason + ")]");
        }
        if (GuardBungee.status) {
            GuardBungee.plugin.getProxy().getPlayers().forEach(proxiedPlayer -> {
                if (proxiedPlayer.getPermissions().contains("epicguard.admin")) {
                    final Title title = GuardBungee.plugin.getProxy().createTitle()
                            .title(new TextComponent(ChatUtil.fix(MessagesBungee.ATTACK_TITLE).replace("{CPS}", String.valueOf(BungeeAttack.getConnectionPerSecond()))))
                            .subTitle(new TextComponent(ChatUtil.fix(MessagesBungee.ATTACK_SUBTITLE).replace("{MAX}", String.valueOf(BungeeAttack.blockedBots))))
                            .fadeIn(0)
                            .fadeOut(10)
                            .stay(10);
                    proxiedPlayer.sendTitle(title);
                    proxiedPlayer.sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatUtil.fix(MessagesBungee.ACTIONBAR_ATTACK)
                            .replace("{NICK}", connection.getName())
                            .replace("{IP}", connection.getAddress().getAddress().getHostAddress())
                            .replace("{DETECTION}", String.valueOf(reason))));
                }
            });
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
