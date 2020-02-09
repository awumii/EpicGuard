package me.ishift.epicguard.bukkit.util.misc;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.manager.user.User;
import me.ishift.epicguard.bukkit.manager.user.UserManager;
import me.ishift.epicguard.bukkit.util.player.ActionBar;
import me.ishift.epicguard.bukkit.util.player.Title;
import me.ishift.epicguard.universal.util.ChatUtil;
import org.bukkit.Bukkit;

public class Notificator {
    public static void title(String title, String subtitle) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final User u = UserManager.getUser(player);
            if (u != null && u.isNotifications()) {
                if (player.hasPermission(GuardBukkit.PERMISSION)) {
                    Title.send(player, title, subtitle, 20, 40, 20);
                }
            }
        });
    }

    public static void broadcast(String text) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final User u = UserManager.getUser(player);
            if (u != null && player.hasPermission(GuardBukkit.PERMISSION)) {
                player.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + text));
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
