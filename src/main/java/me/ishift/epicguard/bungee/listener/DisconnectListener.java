package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.DisconnectHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DisconnectListener extends DisconnectHandler implements Listener {
    public DisconnectListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @EventHandler
    public void onPostLogin(PlayerDisconnectEvent event) {
        this.handle(event.getPlayer().getUniqueId());
    }
}
