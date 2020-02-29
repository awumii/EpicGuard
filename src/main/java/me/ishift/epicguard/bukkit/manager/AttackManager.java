package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.bukkit.listener.player.PlayerPreLoginListener;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import me.ishift.epicguard.universal.types.Reason;
import me.ishift.epicguard.universal.util.GeoAPI;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AttackManager {
    public static void handleDetection(String nick, String address, AsyncPlayerPreLoginEvent event, Reason reason, boolean blacklist) {
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, reason.getReason());
        if (blacklist) StorageManager.blacklist(address);

        PlayerPreLoginListener.setLastPlayer(nick);
        PlayerPreLoginListener.setLastAddress(address);
        PlayerPreLoginListener.setLastCountry(GeoAPI.getCountryCode(GeoAPI.getAddress(address)));
        PlayerPreLoginListener.setLastDetection(reason.name());
        PlayerPreLoginListener.setBlacklisted(blacklist);

        StorageManager.increaseBlockedBots();
        SpeedCheck.setTotalBots(SpeedCheck.getTotalBots() + 1);
    }
}
