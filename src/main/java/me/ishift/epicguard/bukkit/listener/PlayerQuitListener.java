package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.DisconnectHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener extends DisconnectHandler implements Listener {

    public PlayerQuitListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.handle(event.getPlayer().getUniqueId());
    }
}
