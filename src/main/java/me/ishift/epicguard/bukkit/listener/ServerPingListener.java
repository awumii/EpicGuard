package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.PingHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener extends PingHandler implements Listener {
    public ServerPingListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        this.handle(event.getAddress().getHostAddress());
    }
}
