package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.universal.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlacklistManager {
    private static List<String> blacklist = new ArrayList<>();
    private static List<String> whitelist = new ArrayList<>();

    public static boolean isBlacklisted(String adress) {
        return blacklist.contains(adress);
    }

    public static boolean isWhitelisted(String adress) {
        return whitelist.contains(adress);
    }

    public static List<String> getBlacklist() {
        return blacklist;
    }

    public static void setBlacklist(List<String> blacklist) {
        BlacklistManager.blacklist = blacklist;
    }

    public static List<String> getWhitelist() {
        return whitelist;
    }

    public static void setWhitelist(List<String> whitelist) {
        BlacklistManager.whitelist = whitelist;
    }

    public static void blacklist(String adress) {
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

    public static void whitelist(String adress) {
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
