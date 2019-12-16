package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.polskistevek.guard.bukkit.GuardPluginBukkit;
import pl.polskistevek.guard.bukkit.manager.AttackManager;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.utils.GeoAPI;
import pl.polskistevek.guard.utils.KickReason;
import pl.polskistevek.guard.utils.Logger;

import java.net.URL;
import java.util.Scanner;

public class PlayerPreLoginListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        try {
            String adress = event.getAddress().getHostAddress();
            String name = event.getName();
            DataFileManager.checkedConnections++;
            Logger.info("###  CHECKING PLAYER " + name + " [" + adress + "]  ###", true);
            AttackManager.handleAttack(AttackManager.AttackType.CONNECT);

            // Country detection
            if (!GuardPluginBukkit.COUNTRY_MODE.equals("DISABLED")) {
                String country = GeoAPI.getDatabase().country(event.getAddress()).getCountry().getIsoCode();
                if (GuardPluginBukkit.COUNTRY_MODE.equals("WHITELIST")) {
                    if (GuardPluginBukkit.COUNTRIES.contains(country)) {
                        Logger.info("# GEO Check - Passed", true);
                    } else {
                        AttackManager.handleDetection("GEO Check", name, adress);
                        BlacklistManager.add(adress);
                        AttackManager.closeConnection(event, KickReason.GEO);
                        Logger.info("# GEO Check - FAILED", true);
                        return;
                    }
                }
                if (GuardPluginBukkit.COUNTRY_MODE.equals("BLACKLIST")) {
                    if (!GuardPluginBukkit.COUNTRIES.contains(country)) {
                        AttackManager.handleDetection("GEO Check", name, adress);
                        BlacklistManager.add(adress);
                        AttackManager.closeConnection(event, KickReason.GEO);
                        Logger.info("# GEO Check - FAILED", true);
                        return;
                    } else {
                        Logger.info("# GEO Check - Passed", true);
                    }
                }
            }

            // Check if antibot is disabled
            if (!GuardPluginBukkit.ANTIBOT) {
                return;
            }

            // Check attack speed.
            if (AttackManager.checkAttackStatus(AttackManager.AttackType.CONNECT)){
                AttackManager.closeConnection(event, KickReason.ATTACK);
                AttackManager.handleDetection("Speed Check", name, adress);
                Logger.info("# ATTACK_SPEED Check - FAILED", true);
                return;
            }

            // Check if player is on whitelist
            if (BlacklistManager.checkWhitelist(adress)) {
                Logger.info("# Whitelist Check - Passed", true);
                return;
            }

            // Check if player is on blacklist
            if (BlacklistManager.check(adress)) {
                AttackManager.handleDetection("Blacklist Check", name, adress);
                Logger.info("# Blacklist Check - FAILED", true);
                AttackManager.closeConnection(event, KickReason.BLACKLIST);
                return;
            }

            if (GuardPluginBukkit.FORCE_REJOIN){
                if (!AttackManager.rejoinData.contains(name)) {
                    AttackManager.handleDetection("Force Rejoin", name, adress);
                    Logger.info("# Force Rejoin - FAILED", true);
                    AttackManager.closeConnection(event, KickReason.VERIFY);
                    AttackManager.rejoinData.add(name);
                    return;
                }
            }

            // Variables for proxy detection
            final String url1 = GuardPluginBukkit.ANTIBOT_QUERY_1.replace("{IP}", adress);
            final String url2 = GuardPluginBukkit.ANTIBOT_QUERY_2.replace("{IP}", adress);
            final String url3 = GuardPluginBukkit.ANTIBOT_QUERY_3.replace("{IP}", adress);

            // Checking for Proxy/VPN
            if (checkUrl(url1)) {
                AttackManager.closeConnection(event, KickReason.PROXY);
                BlacklistManager.add(adress);
                AttackManager.handleDetection("Proxy Check", name, adress);
                Logger.info("# Proxy Check - FAILED", true);
                return;
            }
            if (checkUrl(url2)) {
                AttackManager.closeConnection(event, KickReason.PROXY);
                BlacklistManager.add(adress);
                AttackManager.handleDetection("Proxy Check", name, adress);
                Logger.info("# Proxy Check - FAILED", true);
                return;
            }
            if (checkUrl(url3)) {
                AttackManager.closeConnection(event, KickReason.PROXY);
                BlacklistManager.add(adress);
                AttackManager.handleDetection("Proxy Check", name, adress);
                Logger.info("# Proxy Check - FAILED", true);
                return;
            }

            // If player has passed every check (event not detected by whitelist)
            Logger.info("###  PLAYER " + name + " [" + adress + "]  PASSED EVERY CHECK ###", true);
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    private static boolean checkUrl(String url) {
        try {
            final Scanner s = new Scanner(new URL(url).openStream());
            Logger.info("# Checking proxy from URL: " + url, true);
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (GuardPluginBukkit.ANTIBOT_QUERY_CONTAINS.contains(s.next())) {
                        Logger.info("# Detected Proxy, URL: " + url, true);
                        return true;
                    }
                }
                Logger.info("# Proxy is not detected from: " + url, true);
                return false;
            }
        } catch (Exception e) {
            Logger.info("EXCEPTION WHILE CHECKING DATA FROM URL: " + url, false);
            Logger.error(e);
            return false;
        }
        return false;
    }
}
