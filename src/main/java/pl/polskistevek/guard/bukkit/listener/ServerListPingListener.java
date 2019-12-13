package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import pl.polskistevek.guard.bukkit.manager.AttackManager;
import pl.polskistevek.guard.utils.Logger;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        try {
            AttackManager.handleAttack(AttackManager.AttackType.PING);
        } catch (Exception ex) {
            Logger.error(ex);
        }
    }
}
