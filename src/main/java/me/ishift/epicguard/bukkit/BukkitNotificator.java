package me.ishift.epicguard.bukkit;

import me.ishift.epicguard.bukkit.util.Reflections;
import me.ishift.epicguard.core.util.Notificator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitNotificator implements Notificator {
    @Override
    public void sendActionBar(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Reflections.sendActionBar(player, message + "  &8░  &7TPS: &6" + Reflections.getTPS());
        }
    }
}
