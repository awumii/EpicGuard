package me.ishift.epicguard.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.DisconnectHandler;

public class DisconnectListener extends DisconnectHandler {
    public DisconnectListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        Player player = event.getPlayer();
        this.handle(player.getUniqueId());
    }
}
