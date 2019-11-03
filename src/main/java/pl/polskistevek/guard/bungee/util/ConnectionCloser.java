package pl.polskistevek.guard.bungee.util;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import pl.polskistevek.guard.bungee.BungeeMain;
import pl.polskistevek.guard.utils.ChatUtil;

public class ConnectionCloser {
    public static void close(PendingConnection connection){
        connection.disconnect(new TextComponent(ChatUtil.fix(BungeeMain.MESSAGE_KICK_PROXY)));
    }
    public static void closeAttack(PendingConnection connection){
        connection.disconnect(new TextComponent(ChatUtil.fix(BungeeMain.MESSAGE_KICK_ATTACK)));
    }
}
