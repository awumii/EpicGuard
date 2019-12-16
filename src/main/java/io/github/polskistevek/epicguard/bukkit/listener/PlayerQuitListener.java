package io.github.polskistevek.epicguard.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import io.github.polskistevek.epicguard.bukkit.manager.UserManager;

public class PlayerQuitListener implements Listener {
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UserManager.removeUser(p);
    }
}
