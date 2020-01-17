package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.manager.BlacklistManager;
import me.ishift.epicguard.bukkit.manager.DataFileManager;
import me.ishift.epicguard.universal.AttackType;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.GeoCheck;
import me.ishift.epicguard.universal.check.NameContainsCheck;
import me.ishift.epicguard.universal.check.ProxyCheck;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.KickReason;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PreLoginListener implements Listener {

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        try {
            final String adress = event.getAddress().getHostAddress();
            final String name = event.getName();

            if (adress.equals("127.0.0.1")) {
                return;
            }

            final String country = GeoAPI.getCountryCode(event.getAddress());
            DataFileManager.checkedConnections++;
            Logger.debug(" ");
            Logger.debug("###### CONNECTION CHECKER - INFO LOG #####");
            Logger.debug("Player: " + name);
            Logger.debug("Adress: " + adress);
            Logger.debug("Country: " + country);
            Logger.debug(" ");
            Logger.debug("# DETECTION LOG:");
            AttackManager.handleAttack(AttackType.CONNECT);

            if (BlacklistManager.checkWhitelist(adress)) {
                Logger.debug("+ Whitelist Check - Passed");
                return;
            }

            if (BlacklistManager.check(adress)) {
                AttackManager.handleDetection("Blacklist", name, adress, event, KickReason.BLACKLIST, false);
                return;
            }

            if (GeoCheck.check(country)) {
                AttackManager.handleDetection("Geographical", name, adress, event, KickReason.GEO, true);
                return;
            }

            if (!Config.antibot) {
                return;
            }

            if (AttackManager.isUnderAttack()) {
                AttackManager.handleDetection("Attack Speed", name, adress, event, KickReason.ATTACK, false);
                return;
            }

            if (NameContainsCheck.check(name)) {
                AttackManager.handleDetection("Name Contains", name, adress, event, KickReason.BLACKLIST, true);
                return;
            }

            if (Config.forceRejoin) {
                if (!AttackManager.rejoinData.contains(name)) {
                    AttackManager.handleDetection("Force Rejoin", name, adress, event, KickReason.VERIFY, false);
                    AttackManager.rejoinData.add(name);
                    return;
                }
            }

            if (ProxyCheck.check(adress)) {
                AttackManager.handleDetection("Proxy/VPN", name, adress, event, KickReason.PROXY, true);
            }
        } catch (Exception e) {
            Logger.throwException(e);
        }
    }
}
