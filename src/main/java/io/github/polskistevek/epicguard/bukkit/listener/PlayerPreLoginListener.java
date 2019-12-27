package io.github.polskistevek.epicguard.bukkit.listener;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.bukkit.manager.AttackManager;
import io.github.polskistevek.epicguard.bukkit.manager.BlacklistManager;
import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import io.github.polskistevek.epicguard.utils.GeoAPI;
import io.github.polskistevek.epicguard.utils.KickReason;
import io.github.polskistevek.epicguard.utils.Logger;

import java.net.URL;
import java.util.Scanner;

public class PlayerPreLoginListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        try {
            final String adress = event.getAddress().getHostAddress();
            final String name = event.getName();
            final String country = GeoAPI.getDatabase().country(event.getAddress()).getCountry().getIsoCode();
            DataFileManager.checkedConnections++;
            Logger.debug("###### CONNECTION CHECKER - INFO LOG #####");
            Logger.debug("Player: " + name);
            Logger.debug("Adress: " + adress);
            Logger.debug("Country: " + country);
            Logger.debug(" ");
            Logger.debug(" # DETECTION LOG:");
            AttackManager.handleAttack(AttackManager.AttackType.CONNECT);

            // Country detection
            if (!GuardBukkit.COUNTRY_MODE.equals("DISABLED")) {
                if (GuardBukkit.COUNTRY_MODE.equals("WHITELIST")) {
                    if (!GuardBukkit.COUNTRIES.contains(country)) {
                        AttackManager.handleDetection("GEO Check", name, adress);
                        BlacklistManager.add(adress);
                        AttackManager.closeConnection(event, KickReason.GEO);
                        Logger.debug("- GEO Check - FAILED");
                        return;
                    }
                    Logger.debug("+ GEO Check - Passed");
                }

                if (GuardBukkit.COUNTRY_MODE.equals("BLACKLIST")) {
                    if (GuardBukkit.COUNTRIES.contains(country)) {
                        AttackManager.handleDetection("GEO Check", name, adress);
                        BlacklistManager.add(adress);
                        AttackManager.closeConnection(event, KickReason.GEO);
                        Logger.debug("- GEO Check - FAILED");
                        return;
                    }
                    Logger.debug("+ GEO Check - Passed");
                }

            }

            if (!GuardBukkit.ANTIBOT) {
                return;
            }

            // Check attack speed.
            if (AttackManager.isUnderAttack()){
                AttackManager.closeConnection(event, KickReason.ATTACK);
                AttackManager.handleDetection("Speed Check", name, adress);
                Logger.debug("- ATTACK_SPEED Check - FAILED");
                return;
            }

            // Check if player is on whitelist
            if (BlacklistManager.checkWhitelist(adress)) {
                Logger.debug("+ Whitelist Check - Passed");
                return;
            }

            // Check if player is on blacklist
            if (BlacklistManager.check(adress)) {
                AttackManager.handleDetection("Blacklist Check", name, adress);
                Logger.debug("- Blacklist Check - FAILED");
                AttackManager.closeConnection(event, KickReason.BLACKLIST);
                return;
            }

            if (GuardBukkit.FORCE_REJOIN){
                if (!AttackManager.rejoinData.contains(name)) {
                    AttackManager.handleDetection("Force Rejoin", name, adress);
                    Logger.debug("- Force Rejoin - FAILED");
                    AttackManager.closeConnection(event, KickReason.VERIFY);
                    AttackManager.rejoinData.add(name);
                    return;
                }
            }

            // Variables for proxy detection
            final String url1 = GuardBukkit.ANTIBOT_QUERY_1.replace("{IP}", adress);
            final String url2 = GuardBukkit.ANTIBOT_QUERY_2.replace("{IP}", adress);
            final String url3 = GuardBukkit.ANTIBOT_QUERY_3.replace("{IP}", adress);

            // Checking for Proxy/VPN
            if (this.checkUrl(url1) || this.checkUrl(url2) || this.checkUrl(url3)) {
                AttackManager.closeConnection(event, KickReason.PROXY);
                BlacklistManager.add(adress);
                AttackManager.handleDetection("Proxy Check", name, adress);
                Logger.debug("- Proxy Check - FAILED");
            }
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    private boolean checkUrl(String url) {
        try {
            final Scanner s = new Scanner(new URL(url).openStream());
            Logger.info("# Checking proxy from URL: " + url);
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (GuardBukkit.ANTIBOT_QUERY_CONTAINS.contains(s.next())) {
                        Logger.debug("# Detected Proxy, URL: " + url);
                        return true;
                    }
                }
                Logger.debug("# Proxy is not detected from: " + url);
                return false;
            }
        } catch (Exception e) {
            Logger.debug("EXCEPTION WHILE CHECKING DATA FROM URL: " + url);
            Logger.throwException(e);
            return false;
        }
        return false;
    }
}
