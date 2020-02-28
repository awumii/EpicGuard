package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.listener.player.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.task.SecondTask;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import me.ishift.epicguard.universal.types.AttackType;
import me.ishift.epicguard.universal.types.Reason;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AttackManager {
    public static void handleDetection(String nick, String address, AsyncPlayerPreLoginEvent event, Reason reason, boolean blacklist) {
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, reason.getReason());

        if (blacklist) StorageManager.blacklist(address);
        PlayerPreLoginListener.setLastPlayer(nick);
        PlayerPreLoginListener.setLastAddress(address);

        try {
            PlayerPreLoginListener.setLastCountry(GeoAPI.getCountryCode(InetAddress.getByName(address)));
        } catch (UnknownHostException ignored) {
        }

        PlayerPreLoginListener.setLastDetection(reason.name());
        PlayerPreLoginListener.setBlacklisted(blacklist);
        StorageManager.increaseBlockedBots();
        SpeedCheck.setTotalBots(SpeedCheck.getTotalBots()+1);
    }

    public static void handleAttack(AttackType type) {
        if (type == AttackType.CONNECT) {
            SpeedCheck.setConnectPerSecond(SpeedCheck.getConnectPerSecond() + 1);
        }
        if (type == AttackType.PING) {
            SpeedCheck.setPingPerSecond(SpeedCheck.getPingPerSecond() + 1);
        }

        Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> {
            if (SpeedCheck.getConnectPerSecond() == 0 || SpeedCheck.getPingPerSecond() == 0) return;
            if (type == AttackType.CONNECT) {
                SpeedCheck.setConnectPerSecond(SpeedCheck.getConnectPerSecond() - 1);
            }
            if (type == AttackType.PING) {
                SpeedCheck.setPingPerSecond(SpeedCheck.getPingPerSecond() - 1);
            }
        }, 20L);
    }
}
