package io.github.polskistevek.epicguard.manager;

import io.github.polskistevek.epicguard.universal.ConfigProvider;

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
        if (ConfigProvider.FIREWALL) {
            try {
                Runtime.getRuntime().exec(ConfigProvider.FIREWALL_BL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void addWhitelist(String adress) {
        IP_WL.add(adress);
        DataFileManager.get().set("whitelist", IP_WL);
        if (ConfigProvider.FIREWALL) {
            try {
                Runtime.getRuntime().exec(ConfigProvider.FIREWALL_WL.replace("{IP}", adress));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        IP_BL.remove(adress);
    }
}
