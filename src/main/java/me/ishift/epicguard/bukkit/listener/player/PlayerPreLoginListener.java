package me.ishift.epicguard.bukkit.listener.player;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.GeoCheck;
import me.ishift.epicguard.universal.check.NameContainsCheck;
import me.ishift.epicguard.universal.check.ProxyCheck;
import me.ishift.epicguard.universal.types.AttackType;
import me.ishift.epicguard.universal.types.KickReason;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {
    private static String lastPlayer = "None";
    private static String lastAddress = "None";
    private static String lastCountry = "None";
    private static String lastDetection = "None";
    private static boolean blacklisted = false;

    public static String getLastPlayer() {
        return lastPlayer;
    }

    public static void setLastPlayer(String lastPlayer) {
        PlayerPreLoginListener.lastPlayer = lastPlayer;
    }

    public static String getLastAddress() {
        return lastAddress;
    }

    public static void setLastAddress(String lastAddress) {
        PlayerPreLoginListener.lastAddress = lastAddress;
    }

    public static String getLastCountry() {
        return lastCountry;
    }

    public static void setLastCountry(String lastCountry) {
        PlayerPreLoginListener.lastCountry = lastCountry;
    }

    public static String getLastDetection() {
        return lastDetection;
    }

    public static void setLastDetection(String lastDetection) {
        PlayerPreLoginListener.lastDetection = lastDetection;
    }

    public static boolean isBlacklisted() {
        return blacklisted;
    }

    public static void setBlacklisted(boolean blacklisted) {
        PlayerPreLoginListener.blacklisted = blacklisted;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        final String address = event.getAddress().getHostAddress();
        final String name = event.getName();

        final String country = GeoAPI.getCountryCode(event.getAddress());
        StorageManager.increaseCheckedConnections();
        Logger.debug(" ");
        Logger.debug("###### CONNECTION CHECKER - INFO LOG #####");
        Logger.debug("Player: " + name);
        Logger.debug("Address: " + address);
        Logger.debug("Country: " + country);
        Logger.debug(" ");
        Logger.debug("# DETECTION LOG:");
        AttackManager.handleAttack(AttackType.CONNECT);

        if (StorageManager.isWhitelisted(address)) {
            Logger.debug("+ Whitelist Check - Passed");
            return;
        }

        if (StorageManager.isBlacklisted(address)) {
            AttackManager.handleDetection("Blacklist", name, address, event, KickReason.BLACKLIST, false);
            return;
        }

        if (GeoCheck.check(country)) {
            AttackManager.handleDetection("Geographical", name, address, event, KickReason.GEO, true);
            return;
        }

        if (!Config.antibot) {
            return;
        }

        if (NameContainsCheck.check(name)) {
            AttackManager.handleDetection("Name Contains", name, address, event, KickReason.BLACKLIST, true);
            return;
        }

        if (AttackManager.isUnderAttack()) {
            AttackManager.handleDetection("Attack Speed", name, address, event, KickReason.ATTACK, false);
            return;
        }

        if (Config.forceRejoin) {
            if (!StorageManager.hasRejoined(name)) {
                AttackManager.handleDetection("Force Rejoin", name, address, event, KickReason.VERIFY, false);
                StorageManager.addRejoined(name);
                return;
            }
        }

        if (ProxyCheck.check(address)) {
            AttackManager.handleDetection("Proxy/VPN", name, address, event, KickReason.PROXY, true);
        }
    }
}
