package me.ishift.epicguard.bukkit.listener.player;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.Logger;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.AttackSpeed;
import me.ishift.epicguard.universal.check.*;
import me.ishift.epicguard.universal.types.AttackType;
import me.ishift.epicguard.universal.GeoAPI;
import me.ishift.epicguard.universal.types.Reason;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        final String address = event.getAddress().getHostAddress();
        final String name = event.getName();
        final String country = GeoAPI.getCountryCode(address);

        if (AttackSpeed.getConnectPerSecond() > Config.connectSpeed || AttackSpeed.getPingPerSecond() > Config.pingSpeed) {
            AttackSpeed.setAttackMode(true);
        }

        StorageManager.increaseCheckedConnections();
        Logger.debug(" ");
        Logger.debug("~-~-~-~-~-~-~-~-~-~-~-~-");
        Logger.debug("Player: " + name);
        Logger.debug("Address: " + address);
        Logger.debug("Country: " + country);
        Logger.debug(" ");

        AttackSpeed.increase(AttackType.CONNECT);
        Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> AttackSpeed.decrease(AttackType.CONNECT), 20L);

        if (StorageManager.isWhitelisted(address)) {
            return;
        }

        if (BlacklistCheck.perform(address)) {
            handleDetection(address, event, Reason.BLACKLIST, false);
            return;
        }

        if (NameContainsCheck.perform(name)) {
            handleDetection(address, event, Reason.NAMECONTAINS, true);
            return;
        }

        if (GeoCheck.perform(address)) {
            handleDetection(address, event, Reason.GEO, true);
            return;
        }

        if (ServerListCheck.perform(address)) {
            handleDetection(address, event, Reason.SERVERLIST, false);
            return;
        }

        if (VerifyCheck.perform(name)) {
            handleDetection(address, event, Reason.VERIFY, false);
            return;
        }

        if (ProxyCheck.perform(address)) {
            handleDetection(address, event, Reason.PROXY, true);
        }
    }

    public static void handleDetection(String address, AsyncPlayerPreLoginEvent event, Reason reason, boolean blacklist) {
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, reason.getReason());
        if (blacklist) StorageManager.blacklist(address);

        StorageManager.increaseBlockedBots();
        AttackSpeed.setTotalBots(AttackSpeed.getTotalBots() + 1);
    }
}
