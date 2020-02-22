package me.ishift.epicguard.universal.cloud;

import me.ishift.epicguard.universal.util.Logger;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CloudManager {
    private static final String CLOUD_URL = "https://raw.githubusercontent.com/PolskiStevek/EpicGuard/master/files/cloud/blacklist.txt";
    private static List<String> cloudBlacklist = new ArrayList<>();
    private static boolean online = false;
    private static String lastCheck;

    public static void connect() {
        try {
            final Scanner s = new Scanner(new URL(CLOUD_URL).openStream());
            final List<String> list = new ArrayList<>();
            if (s.hasNext()) {
                while (s.hasNextLine()) {
                    list.add(s.nextLine());
                }
            }
            // Do not update blacklist if there is no change in cloud.
            if (cloudBlacklist.size() == list.size()) return;

            Logger.debug("[CLOUD] Checking data from the cloud...");
            s.close();
            online = true;
            lastCheck = new SimpleDateFormat("HH:mm").format(new Date());
            Logger.debug("[CLOUD] Operation succesfully completed.");
            cloudBlacklist = list;
        } catch (Exception e) {
            Logger.info("[CLOUD] Error occurred in cloud connecton!");
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
