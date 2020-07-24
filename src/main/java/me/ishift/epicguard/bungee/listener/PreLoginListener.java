package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.DetectionHandler;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLoginListener extends DetectionHandler implements Listener {
    public PreLoginListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        PendingConnection connection = event.getConnection();
        String address = connection.getAddress().getAddress().getHostAddress();
        String nickname = connection.getName();

        if (this.handle(address, nickname)) {
            event.setCancelled(true);
            event.setCancelReason(TextComponent.fromLegacyText(this.getKickMessage()));
        }
    }
}
