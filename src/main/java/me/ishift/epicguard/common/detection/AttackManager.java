package me.ishift.epicguard.common.detection;

import me.ishift.epicguard.common.data.StorageManager;
import me.ishift.epicguard.common.data.config.Configuration;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.util.LibraryLoader;
import me.ishift.epicguard.common.util.GeoApi;

import java.util.Collection;
import java.util.HashSet;

public class AttackManager {
    private static final Collection<ProxyChecker> CHECKERS = new HashSet<>();
    private static GeoApi geoApi;

    private static int connectPerSecond = 0;
    private static int totalBots = 0;
    private static boolean attackMode = false;

    public static void init() {
        Configuration.load();
        Messages.load();
        LibraryLoader.init();
        StorageManager.load();

        if (Configuration.countryEnabled) {
            geoApi = new GeoApi("plugins/EpicGuard", true, Configuration.cityEnabled);
        }
    }

    public static boolean check(String address, String nickname) {
        final Detection detection = new Detection(address, nickname);
        return detection.isDetected();
    }

    public static GeoApi getGeoApi() {
        return geoApi;
    }

    public static void increaseConnectPerSecond() {
        connectPerSecond++;
    }

    public static void setConnectPerSecond(int i) {
        connectPerSecond = i;
    }

    public static void increaseBots() {
        totalBots++;
    }

    public static boolean isUnderAttack() {
        return attackMode;
    }

    public static int getConnectPerSecond() {
        return connectPerSecond;
    }

    public static int getTotalBots() {
        return totalBots;
    }

    public static Collection<ProxyChecker> getCheckers() {
        return CHECKERS;
    }

    public static void setAttackMode(boolean bol) {
        attackMode = bol;
    }

    public static void reset() {
        attackMode = false;
        totalBots = 0;
    }
}
