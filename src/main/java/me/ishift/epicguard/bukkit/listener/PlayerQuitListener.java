package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.core.EpicGuard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    private final EpicGuard epicGuard;

    public PlayerQuitListener(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.epicGuard.getUserManager().removeUser(event.getPlayer().getUniqueId());
    }
}
