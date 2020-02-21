package me.ishift.epicguard.universal;

import de.leonhard.storage.Json;
import me.ishift.epicguard.universal.util.RuntimeExecutor;

import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private static final Json FILE = new Json("storage", "plugins/EpicGuard/data");
    private static List<String> blacklist = new ArrayList<>();
    private static List<String> whitelist = new ArrayList<>();
    public static List<String> rejoinData = new ArrayList<>();
    private static int blockedBots = 0;
    private static int checkedConnections = 0;

    public static void load() {
        blacklist = FILE.getStringList("addresses.blacklist");
        whitelist = FILE.getStringList("addresses.whitelist");
        rejoinData = FILE.getStringList("addresses.rejoin-data");
        blockedBots = FILE.getInt("stats.blocked-bots");
        checkedConnections = FILE.getInt("stats.checked-connections");
    }

    public static void save() {
        FILE.set("addresses.blacklist", blacklist);
        FILE.set("addresses.whitelist", whitelist);
        FILE.set("stats.blocked-bots", blockedBots);
        FILE.set("stats.checked-connections", checkedConnections);
        FILE.set("addresses.rejoin-data", rejoinData);
    }

    public static Json getFile() {
        return FILE;
    }

    public static boolean hasRejoined(String name) {
        return rejoinData.contains(name);
    }

    public static void addRejoined(String name) {
        rejoinData.add(name);
    }

    public static boolean isBlacklisted(String adress) {
        return blacklist.contains(adress);
    }

    public static boolean isWhitelisted(String adress) {
        return whitelist.contains(adress);
    }

    public static List<String> getBlacklist() {
        return blacklist;
    }

    public static List<String> getWhitelist() {
        return whitelist;
    }

    public static void blacklist(String adress) {
        if (blacklist.contains(adress)) return;
        blacklist.add(adress);
        RuntimeExecutor.blacklist(adress);
    }

    public static void whitelist(String adress) {
        if (whitelist.contains(adress)) return;
        whitelist.add(adress);
        blacklist.remove(adress);
        RuntimeExecutor.blacklist(adress);
    }

    public static int getBlockedBots() {
        return blockedBots;
    }

    public static int getCheckedConnections() {
        return checkedConnections;
    }

    public static void increaseBlockedBots() {
        blockedBots++;
    }

    public static void increaseCheckedConnections() {
        checkedConnections++;
    }
}
