package io.github.polskistevek.epicguard.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.utils.ChatUtil;

public class Notificator {
    public static void title(String title, String subtitle) {
        if (GuardBukkit.STATUS) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(GuardBukkit.PERMISSION)) {
                    TitleAPI.sendTitle(p, 0, 20, 20, title, subtitle);
                }
            }
        }
    }

    public static void broadcast(String text) {
        if (GuardBukkit.STATUS) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(GuardBukkit.PERMISSION)) {
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + text));
                }
            }
        }
    }

    public static void action(String text) {
        if (GuardBukkit.STATUS) {
            ActionBarAPI.sendActionBarToAllPlayers(ChatUtil.fix(text), -1, GuardBukkit.PERMISSION);
        }
    }
}
