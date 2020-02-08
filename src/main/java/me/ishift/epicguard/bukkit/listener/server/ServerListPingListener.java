package me.ishift.epicguard.bukkit.listener.server;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.types.AttackType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        AttackManager.handleAttack(AttackType.PING);
        if (AttackManager.getPingPerSecond() > Config.pingSpeed) {
            if (Config.bandwidthOptimizer) {
                event.setMotd("");
                event.setMaxPlayers(0);
            }
        }
    }
}
