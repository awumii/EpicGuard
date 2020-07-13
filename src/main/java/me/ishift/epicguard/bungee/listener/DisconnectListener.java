package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.core.EpicGuard;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DisconnectListener implements Listener {
    private final EpicGuard epicGuard;

    public DisconnectListener(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @EventHandler
    public void onPostLogin(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        this.epicGuard.getUserManager().removeUser(player.getUniqueId());
    }
}
