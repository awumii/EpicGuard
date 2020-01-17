package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.universal.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlacklistManager {
    public static List<String> blacklist = new ArrayList<>();
    public static List<String> whitelist = new ArrayList<>();

    public static boolean check(String adress) {
        return blacklist.contains(adress);
    }

    public static boolean checkWhitelist(String adress) {
        return whitelist.contains(adress);
    }

    public static void add(String adress) {
        if (!blacklist.contains(adress)) {
            blacklist.add(adress);
            DataFileManager.getDataFile().set("blacklist", blacklist);
            if (Config.firewallEnabled) {
                try {
                    Runtime.getRuntime().exec(Config.firewallBlacklistCommand.replace("{IP}", adress));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void addWhitelist(String adress) {
        if (!whitelist.contains(adress)) {
            whitelist.add(adress);
            DataFileManager.getDataFile().set("whitelist", whitelist);
            if (Config.firewallEnabled) {
                try {
                    Runtime.getRuntime().exec(Config.firewallWhitelistCommand.replace("{IP}", adress));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        blacklist.remove(adress);
    }
}
