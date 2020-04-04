package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.common.detection.Detection;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        final PendingConnection connection = event.getConnection();
        final String address = connection.getAddress().getAddress().getHostAddress();
        final String name = connection.getName();

        final Detection detection = new Detection(address, name);
        if (detection.isDetected()) {
            event.setCancelled(true);
            event.setCancelReason(TextComponent.fromLegacyText(detection.getReason().getMessage()));
        }
    }
}
