package me.ishift.epicguard.bukkit.util;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.manager.UserManager;
import me.ishift.epicguard.bukkit.object.User;
import me.ishift.epicguard.bukkit.util.nms.ActionBarAPI;
import me.ishift.epicguard.bukkit.util.nms.TitleAPI;
import me.ishift.epicguard.universal.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Notificator {
    public static void title(String title, String subtitle) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            final User u = UserManager.getUser(p);
            if (u.isNotifications()) {
                if (p.hasPermission(GuardBukkit.PERMISSION)) {
                    TitleAPI.sendTitle(p, 0, 20, 40, title, subtitle);
                }
            }
        }
    }

    public static void broadcast(String text) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            final User u = UserManager.getUser(p);
            if (u.isNotifications()) {
                if (p.hasPermission(GuardBukkit.PERMISSION)) {
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + text));
                }
            }
        }
    }

    public static void action(String text) {
        ActionBarAPI.sendActionBarToAllPlayers(ChatUtil.fix(text), -1, GuardBukkit.PERMISSION);
    }
}
