package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import pl.polskistevek.guard.bukkit.manager.AttackManager;
import pl.polskistevek.guard.utils.Logger;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        AttackManager.pingPerSecond++;
        if (AttackManager.check(AttackManager.AttackType.PING)) {
            Logger.info("SERVER IS BEING ATTACKED! Catched Ping: " + e.getAddress().getHostAddress(), true);
        }
        PreLoginListener.remove(1);
    }
}
