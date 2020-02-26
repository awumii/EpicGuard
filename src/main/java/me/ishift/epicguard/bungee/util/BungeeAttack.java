package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import me.ishift.epicguard.universal.types.AttackType;
import net.md_5.bungee.api.ProxyServer;

import java.util.concurrent.TimeUnit;

public class BungeeAttack {
    public static void handle(AttackType type) {
        if (type == AttackType.CONNECT) {
            SpeedCheck.setConnectPerSecond(SpeedCheck.getConnectPerSecond() + 1);
        }
        if (type == AttackType.PING) {
            SpeedCheck.setPingPerSecond(SpeedCheck.getPingPerSecond() + 1);
        }

        ProxyServer.getInstance().getScheduler().schedule(GuardBungee.getInstance(), () -> {
            if (type == AttackType.CONNECT) {
                SpeedCheck.setConnectPerSecond(SpeedCheck.getConnectPerSecond() - 1);
            }
            if (type == AttackType.PING) {
                SpeedCheck.setPingPerSecond(SpeedCheck.getPingPerSecond() - 1);
            }
        }, 1, TimeUnit.SECONDS);
    }
}
