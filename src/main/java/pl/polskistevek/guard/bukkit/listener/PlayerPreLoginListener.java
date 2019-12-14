package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.manager.AttackManager;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.utils.GeoAPI;
import pl.polskistevek.guard.utils.KickReason;
import pl.polskistevek.guard.utils.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class PlayerPreLoginListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        try {
            String adress = e.getAddress().getHostAddress();
            String name = e.getName();
            DataFileManager.checkedConnections++;
            Logger.info("###  CHECKING PLAYER " + name + " [" + adress + "]  ###", true);
            AttackManager.handleAttack(AttackManager.AttackType.CONNECT);

            // Country detection
            if (!BukkitMain.COUNTRY_MODE.equals("DISABLED")) {
                String country = GeoAPI.dbReader.country(e.getAddress()).getCountry().getIsoCode();
                if (BukkitMain.COUNTRY_MODE.equals("WHITELIST")) {
                    if (BukkitMain.COUNTRIES.contains(country)) {
                        Logger.info("# GEO Check - Passed", true);
                    } else {
                        AttackManager.handleDetection("GEO Check", name, adress);
                        BlacklistManager.add(adress);
                        AttackManager.closeConnection(e, KickReason.GEO);
                        Logger.info("# GEO Check - FAILED", true);
                        return;
                    }
                }
                if (BukkitMain.COUNTRY_MODE.equals("BLACKLIST")) {
                    if (!BukkitMain.COUNTRIES.contains(country)) {
                        AttackManager.handleDetection("GEO Check", name, adress);
                        BlacklistManager.add(adress);
                        AttackManager.closeConnection(e, KickReason.GEO);
                        Logger.info("# GEO Check - FAILED", true);
                        return;
                    } else {
                        Logger.info("# GEO Check - Passed", true);
                    }
                }
            }

            // Check if antibot is disabled
            if (!BukkitMain.ANTIBOT) {
                return;
            }

            // Check attack speed.
            if (AttackManager.checkAttackStatus(AttackManager.AttackType.CONNECT)){
                AttackManager.closeConnection(e, KickReason.ATTACK);
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
                AttackManager.closeConnection(e, KickReason.BLACKLIST);
                return;
            }

            // Variables for proxy detection
            final String url1 = BukkitMain.ANTIBOT_QUERY_1.replace("{IP}", adress);
            final String url2 = BukkitMain.ANTIBOT_QUERY_2.replace("{IP}", adress);
            final String url3 = BukkitMain.ANTIBOT_QUERY_3.replace("{IP}", adress);

            // Checking for Proxy/VPN
            if (checkUrl(url1)) {
                AttackManager.closeConnection(e, KickReason.PROXY);
                BlacklistManager.add(adress);
                AttackManager.handleDetection("Proxy Check", name, adress);
                Logger.info("# Proxy Check - FAILED", true);
                return;
            }
            if (checkUrl(url2)) {
                AttackManager.closeConnection(e, KickReason.PROXY);
                BlacklistManager.add(adress);
                AttackManager.handleDetection("Proxy Check", name, adress);
                Logger.info("# Proxy Check - FAILED", true);
                return;
            }
            if (checkUrl(url3)) {
                AttackManager.closeConnection(e, KickReason.PROXY);
                BlacklistManager.add(adress);
                AttackManager.handleDetection("Proxy Check", name, adress);
                Logger.info("# Proxy Check - FAILED", true);
                return;
            }

            // If player has passed every check (event not detected by whitelist)
            Logger.info("###  PLAYER " + name + " [" + adress + "]  PASSED EVERY CHECK ###", true);
        } catch (Exception ex) {
            Logger.error(ex);
        }
    }

    private static boolean checkUrl(String url) {
        try {
            final Scanner s = new Scanner(new URL(url).openStream());
            if (s.hasNextLine()) {
                while (s.hasNext()) {
                    if (BukkitMain.ANTIBOT_QUERY_CONTAINS.contains(s.next())) {
                        return true;
                    }
                }
                return false;
            }
        } catch (IOException e) {
            Logger.info("[RATE LIMIT] Website returned code 403, it may be down, or rate limited. URL: " + url, true);
            return false;
        }
        return false;
    }
}
