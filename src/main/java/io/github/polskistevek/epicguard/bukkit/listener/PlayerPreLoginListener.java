package io.github.polskistevek.epicguard.bukkit.listener;

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
            DataFileManager.checkedConnections++;
            Logger.info("-------------------------------", true);
            Logger.info("Player: " + name, true);
            Logger.info("Adress: " + adress, true);
            AttackManager.handleAttack(AttackManager.AttackType.CONNECT);

            // Country detection
            if (!GuardBukkit.COUNTRY_MODE.equals("DISABLED")) {
                final String country = GeoAPI.getDatabase().country(event.getAddress()).getCountry().getIsoCode();
                Logger.info("Country: " + country, true);
                Logger.info(" ", true);
                Logger.info(" # DETECTION LOG:", true);

                if (GuardBukkit.COUNTRY_MODE.equals("WHITELIST")) {
                    if (GuardBukkit.COUNTRIES.contains(country)) {
                        AttackManager.handleDetection("GEO Check", name, adress);
                        BlacklistManager.add(adress);
                        AttackManager.closeConnection(event, KickReason.GEO);
                        Logger.info("- GEO Check - FAILED", true);
                        return;
                    }
                    Logger.info("+ GEO Check - Passed", true);
                }

                if (GuardBukkit.COUNTRY_MODE.equals("BLACKLIST")) {
                    if (!GuardBukkit.COUNTRIES.contains(country)) {
                        AttackManager.handleDetection("GEO Check", name, adress);
                        BlacklistManager.add(adress);
                        AttackManager.closeConnection(event, KickReason.GEO);
                        Logger.info("- GEO Check - FAILED", true);
                        return;
                    }
                    Logger.info("+ GEO Check - Passed", true);
                }

            }

            if (!GuardBukkit.ANTIBOT) {
                return;
            }

            // Check attack speed.
            if (AttackManager.isUnderAttack()){
                AttackManager.closeConnection(event, KickReason.ATTACK);
                AttackManager.handleDetection("Speed Check", name, adress);
                Logger.info("- ATTACK_SPEED Check - FAILED", true);
                return;
            }

            // Check if player is on whitelist
            if (BlacklistManager.checkWhitelist(adress)) {
                Logger.info("+ Whitelist Check - Passed", true);
                return;
            }

            // Check if player is on blacklist
            if (BlacklistManager.check(adress)) {
                AttackManager.handleDetection("Blacklist Check", name, adress);
                Logger.info("- Blacklist Check - FAILED", true);
                AttackManager.closeConnection(event, KickReason.BLACKLIST);
                return;
            }

            if (GuardBukkit.FORCE_REJOIN){
                if (!AttackManager.rejoinData.contains(name)) {
                    AttackManager.handleDetection("Force Rejoin", name, adress);
                    Logger.info("- Force Rejoin - FAILED", true);
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
                Logger.info("- Proxy Check - FAILED", true);
                return;
            }

            // If player has passed every check (event not detected by whitelist)
            Logger.info("", true);
            Logger.info("Player has passed successfully, without any detections.", true);
            Logger.info("-------------------", true);
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }

    private boolean checkUrl(String url) {
        try {
            final Scanner s = new Scanner(new URL(url).openStream());
            Logger.info("# Checking proxy from URL: " + url, true);
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (GuardBukkit.ANTIBOT_QUERY_CONTAINS.contains(s.next())) {
                        Logger.info("# Detected Proxy, URL: " + url, true);
                        return true;
                    }
                }
                Logger.info("# Proxy is not detected from: " + url, true);
                return false;
            }
        } catch (Exception e) {
            Logger.info("EXCEPTION WHILE CHECKING DATA FROM URL: " + url, false);
            Logger.throwException(e);
            return false;
        }
        return false;
    }
}
