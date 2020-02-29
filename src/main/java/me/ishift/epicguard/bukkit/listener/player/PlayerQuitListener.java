package me.ishift.epicguard.bukkit.listener.player;

import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.universal.StorageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final String adress = player.getAddress().getAddress().getHostAddress();
        UserManager.removeUser(player);

        // Anti Bypass
        if (StorageManager.isBlacklisted(adress)) {
            event.setQuitMessage("");
        }
    }
}
