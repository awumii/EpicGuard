package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.universal.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FirewallManager {
    public static List<String> blackList = new ArrayList<>();
    public static List<String> whiiteList = new ArrayList<>();

    public static void blacklist(String adress) {
        blackList.add(adress);
        if (Config.FIREWALL) {
            try {
                Runtime.getRuntime().exec(Config.FIREWALL_BL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void whitelist(String adress) {
        whiiteList.add(adress);
        if (Config.FIREWALL) {
            try {
                Runtime.getRuntime().exec(Config.FIREWALL_WL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
