package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.JoinHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener extends JoinHandler implements Listener {
    public PlayerJoinListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.handle(player.getUniqueId(), player.getAddress().getAddress().getHostAddress());
    }
}
