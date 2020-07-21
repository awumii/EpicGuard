package me.ishift.epicguard.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.proxy.Player;
import me.ishift.epicguard.core.EpicGuard;

public class DisconnectListener {
    private final EpicGuard epicGuard;

    public DisconnectListener(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent event) {
        Player player = event.getPlayer();
        this.epicGuard.getUserManager().removeUser(player.getUniqueId());
    }
}
