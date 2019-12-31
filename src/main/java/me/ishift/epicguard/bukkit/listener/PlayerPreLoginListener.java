package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.manager.BlacklistManager;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.GeoCheck;
import me.ishift.epicguard.universal.check.ProxyCheck;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.KickReason;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

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

            if (BlacklistManager.checkWhitelist(adress)) {
                Logger.debug("+ Whitelist Check - Passed");
                return;
            }

            if (GeoCheck.check(country)) {
                AttackManager.handleDetection("GEO Check", name, adress);
                BlacklistManager.add(adress);
                AttackManager.closeConnection(event, KickReason.GEO);
                Logger.debug("- GEO Check - FAILED");
            }

            if (!Config.ANTIBOT) {
                return;
            }

            // Check attack speed.
            if (AttackManager.isUnderAttack()) {
                AttackManager.closeConnection(event, KickReason.ATTACK);
                AttackManager.handleDetection("Speed Check", name, adress);
                Logger.debug("- ATTACK_SPEED Check - FAILED");
                return;
            }

            // Check if player is on blacklist
            if (BlacklistManager.check(adress)) {
                AttackManager.handleDetection("Blacklist Check", name, adress);
                Logger.debug("- Blacklist Check - FAILED");
                AttackManager.closeConnection(event, KickReason.BLACKLIST);
                return;
            }

            if (Config.FORCE_REJOIN) {
                if (!AttackManager.rejoinData.contains(name)) {
                    AttackManager.handleDetection("Force Rejoin", name, adress);
                    Logger.debug("- Force Rejoin - FAILED");
                    AttackManager.closeConnection(event, KickReason.VERIFY);
                    AttackManager.rejoinData.add(name);
                    return;
                }
            }

            if (ProxyCheck.check(adress)) {
                AttackManager.closeConnection(event, KickReason.PROXY);
                BlacklistManager.add(adress);
                AttackManager.handleDetection("Proxy Check", name, adress);
                Logger.debug("- Proxy Check - FAILED");
            }
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}
