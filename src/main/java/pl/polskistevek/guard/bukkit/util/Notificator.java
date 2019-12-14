package pl.polskistevek.guard.bukkit.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.polskistevek.guard.bukkit.GuardPluginBukkit;
import pl.polskistevek.guard.utils.ChatUtil;

public class Notificator {
    public static void title(String title, String subtitle) {
        if (GuardPluginBukkit.STATUS) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(GuardPluginBukkit.PERMISSION)) {
                    TitleAPI.sendTitle(p, 3, 80, 3, title, subtitle);
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
