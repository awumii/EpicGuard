package pl.polskistevek.guard.bungee.listener;

import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import pl.polskistevek.guard.bungee.BungeeMain;

public class ProxyPingListener implements Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent e) {
        PendingConnection c = e.getConnection();
        if (BungeeMain.ANTIBOT) {
            PreLoginListener.cps_ping++;
            if (PreLoginListener.cps_ping > BungeeMain.CPS_PING_ACTIVATE) {
                PreLoginListener.attack = true;
            }
            PreLoginListener.remove(1);
        }
    }
}
