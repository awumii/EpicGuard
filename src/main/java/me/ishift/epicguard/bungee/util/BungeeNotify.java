package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.EpicGuardBungee;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeNotify {
    public static void notify(String message) {
        for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
            if (EpicGuardBungee.getInstance().getStatusPlayers().contains(player.getUniqueId())) {
                BungeeUtil.sendActionBar(player, message);
            }
        }
    }
}
