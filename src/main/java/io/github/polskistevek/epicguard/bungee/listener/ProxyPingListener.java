package io.github.polskistevek.epicguard.bungee.listener;

import io.github.polskistevek.epicguard.bungee.GuardBungee;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent e) {
        PendingConnection c = e.getConnection();
        if (GuardBungee.ANTIBOT) {
            ProxyPreLoginListener.cps_ping++;
            if (ProxyPreLoginListener.cps_ping > GuardBungee.CPS_PING_ACTIVATE) {
                ProxyPreLoginListener.attack = true;
            }
            ProxyPreLoginListener.remove(1);
        }
    }
}
