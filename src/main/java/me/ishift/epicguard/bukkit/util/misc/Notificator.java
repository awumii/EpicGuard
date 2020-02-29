package me.ishift.epicguard.bukkit.util.misc;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.user.User;
import me.ishift.epicguard.bukkit.user.UserManager;
import me.ishift.epicguard.bukkit.util.player.ActionBar;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.util.ChatUtil;
import org.bukkit.Bukkit;

public class Notificator {
    public static void broadcast(String text) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final User u = UserManager.getUser(player);
            if (u != null && player.hasPermission(GuardBukkit.PERMISSION)) {
                player.sendMessage(ChatUtil.fix(Messages.prefix + text));
            }
        });
    }

    public static void action(String text) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final User u = UserManager.getUser(player);
            if (u != null && u.isNotifications()) {
                if (player.hasPermission(GuardBukkit.PERMISSION)) {
                    ActionBar.sendActionBar(player, text);
                }
            }
        });
    }
}
