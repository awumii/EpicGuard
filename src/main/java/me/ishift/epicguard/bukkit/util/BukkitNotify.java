package me.ishift.epicguard.bukkit.util;

import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.common.AttackManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BukkitNotify {
    public static void notify(String message, AttackManager manager) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            final User user = new User(player.getName(), manager);
            if (user.isNotifications()) {
                ActionBarAPI.sendActionBar(player, message);
            }
        }
    }
}
