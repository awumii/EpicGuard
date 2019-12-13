package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.polskistevek.guard.bukkit.manager.UserManager;

public class PlayerQuitListener implements Listener {
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UserManager.removeUser(p);
    }
}
