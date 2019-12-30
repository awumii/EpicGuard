package io.github.polskistevek.epicguard.bungee.util;

import io.github.polskistevek.epicguard.universal.util.ChatUtil;
import io.github.polskistevek.epicguard.universal.util.KickReason;
import io.github.polskistevek.epicguard.universal.util.Logger;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;

public class PendingConnectionCloser {
    public static void close(PendingConnection connection, KickReason reason) {
        Logger.info("[ConnectionCloser] Closing connection " + connection.getAddress().getAddress().getHostAddress() + " (" + connection.getName() + "), reason: " + reason);
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
    }
}
