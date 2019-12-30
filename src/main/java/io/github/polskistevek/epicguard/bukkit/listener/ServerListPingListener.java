package io.github.polskistevek.epicguard.bukkit.listener;

import io.github.polskistevek.epicguard.bukkit.AttackManager_Spigot;
import io.github.polskistevek.epicguard.universal.AttackManager;
import io.github.polskistevek.epicguard.universal.AttackType;
import io.github.polskistevek.epicguard.universal.util.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        try {
            AttackManager.handleAttack(AttackType.PING);
            AttackManager_Spigot.runTask(AttackType.PING);
            if (AttackManager.isUnderAttack()) {
                e.setMotd("");
                e.setMaxPlayers(0);
            }
        } catch (Exception ex) {
            Logger.throwException(ex);
        }
    }
}
