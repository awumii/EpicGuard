package io.github.polskistevek.epicguard.bukkit.listener;

import io.github.polskistevek.epicguard.bukkit.AttackManager_Spigot;
import io.github.polskistevek.epicguard.bukkit.manager.BlacklistManager;
import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import io.github.polskistevek.epicguard.universal.AttackManager;
import io.github.polskistevek.epicguard.universal.AttackType;
import io.github.polskistevek.epicguard.universal.ConfigProvider;
import io.github.polskistevek.epicguard.universal.check.GeoCheck;
import io.github.polskistevek.epicguard.universal.check.ProxyCheck;
import io.github.polskistevek.epicguard.universal.check.RejoinCheck;
import io.github.polskistevek.epicguard.universal.util.GeoDataase;
import io.github.polskistevek.epicguard.universal.util.KickReason;
import io.github.polskistevek.epicguard.universal.util.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        try {
            final String adress = event.getAddress().getHostAddress();
            final String name = event.getName();
            final String country = GeoDataase.getDatabase().country(event.getAddress()).getCountry().getIsoCode();
            DataFileManager.checkedConnections++;
            Logger.debug("###### CONNECTION CHECKER - INFO LOG #####");
            Logger.debug("Player: " + name);
            Logger.debug("Adress: " + adress);
            Logger.debug("Country: " + country);
            Logger.debug(" ");
            Logger.debug(" # DETECTION LOG:");
            AttackManager.handleAttack(AttackType.CONNECT);

            // Country detection
            if (GeoCheck.check(country)) {
                AttackManager_Spigot.handleDetection("GEO Check", name, adress);
                BlacklistManager.add(adress);
                AttackManager_Spigot.closeConnection(event, KickReason.GEO);
                Logger.debug("- GEO Check - FAILED");
            }
            Logger.debug("+ GEO Check - Passed");


            if (!ConfigProvider.ANTIBOT) {
                return;
            }

            // Check attack speed.
            if (AttackManager.isUnderAttack()){
                AttackManager_Spigot.closeConnection(event, KickReason.ATTACK);
                AttackManager_Spigot.handleDetection("Speed Check", name, adress);
                Logger.debug("- ATTACK_SPEED Check - FAILED");
                return;
            }

            if (BlacklistManager.checkWhitelist(adress)) {
                Logger.debug("+ Whitelist Check - Passed");
                return;
            }

            if (BlacklistManager.check(adress)) {
                AttackManager_Spigot.handleDetection("Blacklist Check", name, adress);
                Logger.debug("- Blacklist Check - FAILED");
                AttackManager_Spigot.closeConnection(event, KickReason.BLACKLIST);
                return;
            }

            if (RejoinCheck.check(name)) {
                AttackManager_Spigot.handleDetection("Force Rejoin", name, adress);
                Logger.debug("- Force Rejoin - FAILED");
                AttackManager_Spigot.closeConnection(event, KickReason.VERIFY);
            }

            // Variables for proxy detection
            if (ProxyCheck.check(adress)) {
                AttackManager_Spigot.closeConnection(event, KickReason.PROXY);
                BlacklistManager.add(adress);
                AttackManager_Spigot.handleDetection("Proxy Check", name, adress);
                Logger.debug("- Proxy Check - FAILED");
            }
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}
