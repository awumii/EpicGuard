package me.ishift.epicguard.bukkit.manager;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.listener.player.PlayerPreLoginListener;
import me.ishift.epicguard.bukkit.task.HeuristicsTask;
import me.ishift.epicguard.universal.Config;
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
        Logger.debug("- " + reason.name() + " - DETECTED");

        if (blacklist) {
            StorageManager.blacklist(address);
            HeuristicsTask.setBlacklistInc(HeuristicsTask.getBlacklistInc() + 1);
            Logger.debug("- This IP has been blacklisted.");
        }
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
        if (SpeedCheck.getConnectPerSecond() > Config.connectSpeed || SpeedCheck.getPingPerSecond() > Config.pingSpeed) {
            SpeedCheck.setAttackMode(true);
        }
        if (type == AttackType.CONNECT) {
            SpeedCheck.setConnectPerSecond(SpeedCheck.getConnectPerSecond() + 1);
        }
        if (type == AttackType.PING) {
            SpeedCheck.setPingPerSecond(SpeedCheck.getPingPerSecond() + 1);
        }

        Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> {
            if (type == AttackType.CONNECT) {
                SpeedCheck.setConnectPerSecond(SpeedCheck.getConnectPerSecond() - 1);
            }
            if (type == AttackType.PING) {
                SpeedCheck.setPingPerSecond(SpeedCheck.getPingPerSecond() - 1);
            }
        }, 20L);
    }
}
