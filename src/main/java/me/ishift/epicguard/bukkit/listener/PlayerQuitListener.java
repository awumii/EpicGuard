package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.manager.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerQuitListener implements Listener {
    public static List<String> hiddenNames = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UserManager.removeUser(p);
        if (hiddenNames.contains(p.getName())) {
            e.setQuitMessage("");
            hiddenNames.remove(p.getName());
        }
    }
}
