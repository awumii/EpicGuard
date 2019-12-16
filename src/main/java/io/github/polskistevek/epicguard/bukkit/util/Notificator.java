package io.github.polskistevek.epicguard.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import io.github.polskistevek.epicguard.bukkit.GuardPluginBukkit;
import io.github.polskistevek.epicguard.utils.ChatUtil;

public class Notificator {
    public static void title(String title, String subtitle) {
        if (GuardPluginBukkit.STATUS) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(GuardPluginBukkit.PERMISSION)) {
                    TitleAPI.sendTitle(p, 0, 20, 20, title, subtitle);
                }
            }
        }
    }

    public static void broadcast(String text) {
        if (GuardPluginBukkit.STATUS) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(GuardPluginBukkit.PERMISSION)) {
                    p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + text));
                }
            }
        }
    }

    public static void action(String text) {
        if (GuardPluginBukkit.STATUS) {
            ActionBarAPI.sendActionBarToAllPlayers(ChatUtil.fix(text), -1, GuardPluginBukkit.PERMISSION);
        }
    }
}
