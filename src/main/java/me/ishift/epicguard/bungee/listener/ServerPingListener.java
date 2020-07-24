package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.PingHandler;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerPingListener extends PingHandler implements Listener {
    public ServerPingListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @EventHandler
    public void onPing(ProxyPingEvent event) {
        this.handle(event.getConnection().getAddress().getAddress().getHostAddress());
    }
}
