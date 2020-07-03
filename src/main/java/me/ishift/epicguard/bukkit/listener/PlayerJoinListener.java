package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.core.EpicGuard;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final EpicGuard epicGuard;

    public PlayerJoinListener(EpicGuard epicGuard) {
        this.epicGuard = epicGuard;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.epicGuard.getUserManager().addUser(event.getPlayer().getUniqueId());
    }
}
