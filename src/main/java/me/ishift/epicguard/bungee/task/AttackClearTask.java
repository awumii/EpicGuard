package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.universal.Config;

import java.util.concurrent.TimeUnit;

public class AttackClearTask implements Runnable {

    @Override
    public void run() {
        if (BungeeAttack.getConnectionPerSecond() < Config.CONNECT_SPEED || BungeeAttack.getPingPerSecond() < Config.PING_SPEED) {
            BungeeAttack.setAttack(false);
        }
    }

    public static void start() {
        GuardBungee.plugin.getProxy().getScheduler().schedule(GuardBungee.plugin, () -> {
            if (BungeeAttack.getConnectionPerSecond() < Config.CONNECT_SPEED || BungeeAttack.getPingPerSecond() < Config.PING_SPEED) {
                BungeeAttack.setAttack(false);
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}
