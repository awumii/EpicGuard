package me.ishift.epicguard.bukkit.listener.server;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.universal.AttackType;
import me.ishift.epicguard.universal.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        AttackManager.handleAttack(AttackType.PING);
        if (AttackManager.getPingPerSecond() > Config.pingSpeed) {
            e.setMotd("");
            e.setMaxPlayers(0);
        }
    }
}
