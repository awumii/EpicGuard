package pl.polskistevek.guard.bukkit.manager;

import pl.polskistevek.guard.bukkit.GuardPluginBukkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlacklistManager {
    public static List<String> IP_BL = new ArrayList<>();
    public static List<String> IP_WL = new ArrayList<>();

    public static boolean check(String adress) {
        return IP_BL.contains(adress);
    }

    public static boolean checkWhitelist(String adress) {
        return IP_WL.contains(adress);
    }

    public static void add(String adress) {
        IP_BL.add(adress);
        DataFileManager.get().set("blacklist", IP_BL);
        if (GuardPluginBukkit.FIREWALL) {
            try {
                Runtime.getRuntime().exec(GuardPluginBukkit.FIREWALL_BL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void addWhitelist(String adress) {
        IP_WL.add(adress);
        DataFileManager.get().set("whitelist", IP_WL);
        if (GuardPluginBukkit.FIREWALL) {
            try {
                Runtime.getRuntime().exec(GuardPluginBukkit.FIREWALL_WL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        IP_BL.remove(adress);
    }
}
