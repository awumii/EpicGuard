package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.DetectionHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener extends DetectionHandler implements Listener {
    public PlayerPreLoginListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        String address = event.getAddress().getHostAddress();
        String nickname = event.getName();

        if (this.handle(address, nickname)) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, this.getKickMessage());
        }
    }
}
