package pl.polskistevek.guard.bungee.task;

import pl.polskistevek.guard.bungee.BungeeMain;
import pl.polskistevek.guard.bungee.listener.ProxyPreLoginListener;

import java.util.concurrent.TimeUnit;

public class AttackClearTask {
    public static void start() {
        BungeeMain.plugin.getProxy().getScheduler().schedule(BungeeMain.plugin, () -> {
            if (ProxyPreLoginListener.cps < BungeeMain.CPS_ACTIVATE) {
                if (ProxyPreLoginListener.cps_ping < BungeeMain.CPS_PING_ACTIVATE) {
                    ProxyPreLoginListener.attack = false;
                }
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}
