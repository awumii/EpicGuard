package me.ishift.epicguard.bungee.util;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.universal.AttackType;
import net.md_5.bungee.api.ProxyServer;

import java.util.concurrent.TimeUnit;

public class BungeeAttack {
    public static int connectionPerSecond = 0;
    public static boolean attack = false;
    public static int pingPerSecond = 0;
    public static int blockedBots = 0;

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

    public static void handle(AttackType type) {
        if (type == AttackType.CONNECT) {
            connectionPerSecond++;
        }
        if (type == AttackType.PING) {
            pingPerSecond++;
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
