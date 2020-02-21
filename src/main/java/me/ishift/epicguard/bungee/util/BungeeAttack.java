package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.types.AttackType;
import net.md_5.bungee.api.ProxyServer;

import java.util.concurrent.TimeUnit;

public class BungeeAttack {
    private static int connectionPerSecond = 0;
    private static boolean attack = false;
    private static int pingPerSecond = 0;
    private static int blockedBots = 0;

    public static boolean isAttack() {
        return attack;
    }

    public static void setAttack(boolean attack) {
        BungeeAttack.attack = attack;
    }

    public static int getConnectionPerSecond() {
        return connectionPerSecond;
    }

    public static int getPingPerSecond() {
        return pingPerSecond;
    }

    public static int getBlockedBots() {
        return blockedBots;
    }

    public static void setBlockedBots(int blockedBots) {
        BungeeAttack.blockedBots = blockedBots;
    }

    public static void handle(AttackType type) {
        if (type == AttackType.CONNECT) {
            connectionPerSecond++;
            if (connectionPerSecond > Config.connectSpeed) {
                attack = true;
            }
        }
        if (type == AttackType.PING) {
            pingPerSecond++;
            if (pingPerSecond > Config.pingSpeed) {
                attack = true;
            }
        }

        ProxyServer.getInstance().getScheduler().schedule(GuardBungee.getInstance(), () -> {
            if (type == AttackType.CONNECT) {
                connectionPerSecond--;
            }
            if (type == AttackType.PING) {
                pingPerSecond--;
            }
        }, 1, TimeUnit.SECONDS);
    }
}
