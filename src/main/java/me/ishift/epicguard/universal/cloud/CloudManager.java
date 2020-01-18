package me.ishift.epicguard.universal.cloud;

import me.ishift.epicguard.universal.util.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CloudManager {
    private static final String CLOUD_BL_URL = "https://raw.githubusercontent.com/PolskiStevek/EpicGuard/master/files/cloud/blacklist.txt";
    private static List<String> CLOUD_BL = new ArrayList<>();
    private static boolean online = false;

    public static void connect(CloudGet cloudGet) {
        if (cloudGet == CloudGet.BLACKLIST) {
            CLOUD_BL = getList(CLOUD_BL_URL);
        }
    }

    private static List<String> getList(String url) {
        try {
            Logger.debug("[CLOUD] Checking data from the cloud...");
            final Scanner s = new Scanner(new URL(url).openStream());
            final List<String> list = new ArrayList<>();
            if (s.hasNext()) {
                while (s.hasNextLine()) {
                    list.add(s.nextLine());
                }
            }
            s.close();
            online = true;
            Logger.debug("[CLOUD] Operation succesfully completed.");
            return list;
        } catch (Exception e) {
            Logger.info("[CLOUD] Error occurred in cloud connecton!");
            Logger.info("[CLOUD] " + e.getMessage());
            online = false;
        }
        return new ArrayList<>();
    }

    public static List<String> getCloudBlacklist() {
        return CLOUD_BL;
    }

    public static boolean isOnline() {
        return online;
    }
}
