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
            AttackManager.checkAttackStatus(AttackManager.AttackType.PING);
            AttackManager.handleAttack(AttackManager.AttackType.PING);
        } catch (Exception ex) {
            Logger.error(ex);
        }
    }
}
