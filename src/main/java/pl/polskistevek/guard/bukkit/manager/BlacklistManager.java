package pl.polskistevek.guard.bukkit.manager;

import org.bukkit.Bukkit;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.ChatUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlacklistManager {
    public static List<String> IP_BL = new ArrayList<>();
    public static List<String> IP_WL = new ArrayList<>();

    public static boolean check(String adress){
        return IP_BL.contains(adress);
    }

    public static boolean checkWhitelist(String adress){
        return IP_WL.contains(adress);
    }

    public static void add(String adress){
        IP_BL.add(adress);
        ConfigManager.get().set("blacklist", IP_BL);
        if (BukkitMain.FIREWALL){
            try {
                Runtime.getRuntime().exec(BukkitMain.FIREWALL_BL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
                Bukkit.broadcast(ChatUtil.fix(BukkitMain.PREFIX + "ERROR WHILE EXECUTING FIREWALL COMMAND! &cCHECK SERVER CONSOLE ASAP."), BukkitMain.PERMISSION);
            }
        }
    }

    public static void addWhitelist(String adress){
        IP_WL.add(adress);
        ConfigManager.get().set("whitelist", IP_WL);
        if (BukkitMain.FIREWALL){
            try {
                Runtime.getRuntime().exec(BukkitMain.FIREWALL_WL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
                Bukkit.broadcast(ChatUtil.fix(BukkitMain.PREFIX + "ERROR WHILE EXECUTING FIREWALL COMMAND! &cCHECK SERVER CONSOLE ASAP."), BukkitMain.PERMISSION);
            }
        }
        if (IP_BL.contains(adress)){
            IP_BL.remove(adress);
        }
    }
}
