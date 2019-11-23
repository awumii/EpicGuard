package pl.polskistevek.guard.bungee.task;

import pl.polskistevek.guard.bungee.BungeeMain;
import pl.polskistevek.guard.bungee.listener.PreLoginListener;
import java.util.concurrent.TimeUnit;

public class AttackClearTask {
    public static void start() {
        BungeeMain.plugin.getProxy().getScheduler().schedule(BungeeMain.plugin, () -> {
            if (PreLoginListener.cps < BungeeMain.CPS_ACTIVATE){
                if (PreLoginListener.cps_ping < BungeeMain.CPS_PING_ACTIVATE){
                    PreLoginListener.attack = false;
                }
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}
