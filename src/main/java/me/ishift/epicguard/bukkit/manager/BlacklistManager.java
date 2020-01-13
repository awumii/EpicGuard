package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.universal.Config;

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
        if (!IP_BL.contains(adress)) {
            IP_BL.add(adress);
            DataFileManager.getDataFile().set("blacklist", IP_BL);
            if (Config.FIREWALL) {
                try {
                    Runtime.getRuntime().exec(Config.FIREWALL_BL.replace("{IP}", adress));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void addWhitelist(String adress) {
        if (!IP_WL.contains(adress)) {
            IP_WL.add(adress);
            DataFileManager.getDataFile().set("whitelist", IP_WL);
            if (Config.FIREWALL) {
                try {
                    Runtime.getRuntime().exec(Config.FIREWALL_WL.replace("{IP}", adress));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        IP_BL.remove(adress);
    }
}
