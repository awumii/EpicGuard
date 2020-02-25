package me.ishift.epicguard.universal.cloud;

import me.ishift.epicguard.universal.Logger;
import me.ishift.epicguard.universal.util.URLUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CloudManager {
    private static final String CLOUD_URL = "https://raw.githubusercontent.com/PolskiStevek/EpicGuard/master/files/cloud/blacklist.txt";
    private static List<String> cloudBlacklist = new ArrayList<>();
    private static boolean online = false;
    private static String lastCheck;

    public static void connect() {
        try {
            cloudBlacklist = URLUtil.readLines(CLOUD_URL);
            online = true;
            lastCheck = new SimpleDateFormat("HH:mm").format(new Date());
        } catch (Exception e) {
            Logger.info("[CLOUD] An error occurred, please contact author!");
            Logger.info("[CLOUD] " + e.getMessage());
            online = false;
        }
    }

    public static List<String> getCloudBlacklist() {
        return cloudBlacklist;
    }

    public static boolean isOnline() {
        return online;
    }

    public static String getLastCheck() {
        return lastCheck;
    }
}
