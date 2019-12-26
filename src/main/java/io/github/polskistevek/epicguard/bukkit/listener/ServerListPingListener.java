package io.github.polskistevek.epicguard.bukkit.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import io.github.polskistevek.epicguard.bukkit.manager.AttackManager;
import io.github.polskistevek.epicguard.utils.Logger;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        try {
            AttackManager.handleAttack(AttackManager.AttackType.PING);
            if (AttackManager.isUnderAttack()) {
                e.setMotd("");
                e.setMaxPlayers(0);
                e.setServerIcon(null);
            }
        } catch (Exception ex) {
            Logger.throwException(ex);
        }
    }
}
