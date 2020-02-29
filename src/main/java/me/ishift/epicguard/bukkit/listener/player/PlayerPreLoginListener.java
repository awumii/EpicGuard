package me.ishift.epicguard.bukkit.listener.player;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.Logger;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.CheckManager;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
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

        StorageManager.increaseCheckedConnections();
        Logger.debug(" ");
        Logger.debug("~-~-~-~-~-~-~-~-~-~-~-~-");
        Logger.debug("Player: " + name);
        Logger.debug("Address: " + address);
        Logger.debug("Country: " + country);
        Logger.debug(" ");

        SpeedCheck.increase(AttackType.CONNECT);
        Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> SpeedCheck.decrease(AttackType.CONNECT), 20L);

        if (StorageManager.isWhitelisted(address)) return;

        CheckManager.getChecks().stream().filter(check -> check.perform(address, name)).findFirst().ifPresent(check -> handleDetection(address, event, check.getReason(), check.shouldBlacklist()));
    }

    public static void handleDetection(String address, AsyncPlayerPreLoginEvent event, Reason reason, boolean blacklist) {
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, reason.getReason());
        if (blacklist) StorageManager.blacklist(address);

        StorageManager.increaseBlockedBots();
        SpeedCheck.setTotalBots(SpeedCheck.getTotalBots() + 1);
    }
}
