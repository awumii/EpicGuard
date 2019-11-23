package pl.polskistevek.guard.bukkit.closer;

import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.polskistevek.guard.bukkit.util.MessagesBukkit;
import pl.polskistevek.guard.utils.KickReason;

public class ConnectionCloser {
    public static void close(AsyncPlayerPreLoginEvent e, KickReason reason){
        if (reason == KickReason.GEO) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_COUNTRY){
                sb.append(s).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.ATTACK) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_ATTACK){
                sb.append(s).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.BLACKLIST) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_BLACKLIST){
                sb.append(s).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }

        if (reason == KickReason.PROXY) {
            StringBuilder sb = new StringBuilder();
            for (String s : MessagesBukkit.MESSAGE_KICK_PROXY){
                sb.append(s).append("\n");
            }
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, sb.toString());
        }
    }
}
