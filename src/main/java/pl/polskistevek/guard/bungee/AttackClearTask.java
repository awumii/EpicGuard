package pl.polskistevek.guard.bungee;

import pl.polskistevek.guard.bungee.listener.ProxyConnectListener;
import java.util.concurrent.TimeUnit;

public class AttackClearTask {
    public static void start() {
        BungeeMain.plugin.getProxy().getScheduler().schedule(BungeeMain.plugin, () -> {
            if (ProxyConnectListener.cps < BungeeMain.CPS_ACTIVATE){
                if (ProxyConnectListener.cps_ping < BungeeMain.CPS_PING_ACTIVATE){
                    ProxyConnectListener.attack = false;
                }
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}
