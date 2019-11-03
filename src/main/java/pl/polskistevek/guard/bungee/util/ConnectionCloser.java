package pl.polskistevek.guard.bungee.util;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import pl.polskistevek.guard.bungee.BungeeMain;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.utils.Logger;

public class ConnectionCloser {
    public static void close(PendingConnection connection){
        Logger.log("[ConnectionCloser] Closing connection " + connection.getAddress().getAddress().getHostAddress() + " (" + connection.getName() + "), reason: KICK_PROXY");
        connection.disconnect(new TextComponent(ChatUtil.fix(BungeeMain.MESSAGE_KICK_PROXY)));
    }
    public static void closeAttack(PendingConnection connection){
        Logger.log("[ConnectionCloser] Closing connection " + connection.getAddress().getAddress().getHostAddress()+ " (" + connection.getName() + "), reason: KICK_ATTACK_MODE");
        connection.disconnect(new TextComponent(ChatUtil.fix(BungeeMain.MESSAGE_KICK_ATTACK)));
    }

    public static void closeGeo(PendingConnection connection){
        Logger.log("[ConnectionCloser] Closing connection " + connection.getAddress().getAddress().getHostAddress() + " (" + connection.getName() + "), reason: KICK_GEO");
        connection.disconnect(new TextComponent(ChatUtil.fix(BungeeMain.MESSAGE_KICK_GEO)));
    }
}
