package me.ishift.epicguard.bungee.task;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.listener.ProxyPreLoginListener;

import java.util.concurrent.TimeUnit;

public class AttackClearTask {
    public static void start() {
        GuardBungee.plugin.getProxy().getScheduler().schedule(GuardBungee.plugin, () -> {
            if (ProxyPreLoginListener.cps < GuardBungee.CPS_ACTIVATE) {
                if (ProxyPreLoginListener.cps_ping < GuardBungee.CPS_PING_ACTIVATE) {
                    ProxyPreLoginListener.attack = false;
                }
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}
