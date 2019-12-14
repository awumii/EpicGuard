package pl.polskistevek.guard.bungee.task;

import pl.polskistevek.guard.bungee.GuardPluginBungee;
import pl.polskistevek.guard.bungee.listener.ProxyPreLoginListener;

import java.util.concurrent.TimeUnit;

public class AttackClearTask {
    public static void start() {
        GuardPluginBungee.plugin.getProxy().getScheduler().schedule(GuardPluginBungee.plugin, () -> {
            if (ProxyPreLoginListener.cps < GuardPluginBungee.CPS_ACTIVATE) {
                if (ProxyPreLoginListener.cps_ping < GuardPluginBungee.CPS_PING_ACTIVATE) {
                    ProxyPreLoginListener.attack = false;
                }
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}
