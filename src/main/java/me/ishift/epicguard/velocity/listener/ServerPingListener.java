package me.ishift.epicguard.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.PingHandler;

public class ServerPingListener extends PingHandler {
    public ServerPingListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Subscribe
    public void onPing(ProxyPingEvent event) {
        this.handle(event.getConnection().getRemoteAddress().getAddress().getHostAddress());
    }
}
