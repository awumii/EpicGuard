package pl.polskistevek.guard.bungee.closer;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import pl.polskistevek.guard.bungee.util.MessagesBungee;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.utils.KickReason;
import pl.polskistevek.guard.utils.Logger;

public class PendingConnectionCloser {
    public static void close(PendingConnection connection, KickReason reason) {
        Logger.info("[ConnectionCloser] Closing connection " + connection.getAddress().getAddress().getHostAddress() + " (" + connection.getName() + "), reason: " + reason, false);
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
