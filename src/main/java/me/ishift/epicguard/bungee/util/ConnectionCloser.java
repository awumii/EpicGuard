package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import me.ishift.epicguard.universal.types.Reason;
import me.ishift.epicguard.universal.Logger;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;

public class ConnectionCloser {
    public static void close(PendingConnection connection, Reason reason) {
        connection.disconnect(new TextComponent(reason.getReason()));
        if (GuardBungee.log) {
            Logger.info("Closing: " + connection.getVirtualHost().getAddress().getHostAddress() + "(" + connection.getName() + "), (" + reason + ")]");
        }

        SpeedCheck.setTotalBots(SpeedCheck.getTotalBots() + 1);
    }
}
