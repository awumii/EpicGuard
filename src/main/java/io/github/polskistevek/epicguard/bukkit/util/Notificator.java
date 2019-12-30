package io.github.polskistevek.epicguard.bukkit.util;

import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.bukkit.manager.UserManager;
import io.github.polskistevek.epicguard.bukkit.object.User;
import io.github.polskistevek.epicguard.bukkit.util.nms.ActionBarAPI;
import io.github.polskistevek.epicguard.bukkit.util.nms.TitleAPI;
import io.github.polskistevek.epicguard.universal.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Notificator {
    public static void title(String title, String subtitle) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            final User u = UserManager.getUser(p);
            if (u.isNotifications()) {
                if (p.hasPermission(GuardBukkit.PERMISSION)) {
                    TitleAPI.sendTitle(p, 0, 20, 20, title, subtitle);
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