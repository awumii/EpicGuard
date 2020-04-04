package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.common.detection.Detection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        final String address = event.getAddress().getHostAddress();
        final String name = event.getName();

        final Detection detection = new Detection(address, name);
        if (detection.isDetected()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, detection.getReason().getMessage());
        }
    }
}
