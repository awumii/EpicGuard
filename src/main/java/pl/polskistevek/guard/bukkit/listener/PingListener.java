package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.utils.Logger;

public class PingListener implements Listener {
    public static int cps_ping;

    @EventHandler
    public void onPing(ServerListPingEvent e){
        cps_ping++;
        if (cps_ping > BukkitMain.PING_SPEED){
            Logger.log("ATTACK_PING ACTIVATED: Catched ping: " + e.getAddress().getHostAddress());
            PreLoginListener.attack = true;
        }
        PreLoginListener.rem(1);
    }
}