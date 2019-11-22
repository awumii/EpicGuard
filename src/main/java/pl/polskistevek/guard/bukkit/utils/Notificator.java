package pl.polskistevek.guard.bukkit.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.ChatUtil;

public class Notificator {
    public static void title(String title, String subtitle){
        if (BukkitMain.STATUS) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(BukkitMain.PERMISSION)) {
                    TitleAPI.sendTitle(p, 3, 80, 3, title, subtitle);
                }
            }
        }
    }

    public static void broadcast(String text){
        if (BukkitMain.STATUS){
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission(BukkitMain.PERMISSION)) {
                    p.sendMessage(ChatUtil.fix(MessageFileManager.PREFIX + text));
                }
            }
        }
    }

    public static void send(Player p, String text){
        if (BukkitMain.STATUS){
            if (p.hasPermission(BukkitMain.PERMISSION)){
                p.sendMessage(ChatUtil.fix(MessageFileManager.PREFIX + text));
            }
        }
    }

    public static void action(String text){
        if (BukkitMain.STATUS) {
            ActionBarAPI.sendActionBarToAllPlayers(ChatUtil.fix(text), -1, BukkitMain.PERMISSION);
        }
    }
}
