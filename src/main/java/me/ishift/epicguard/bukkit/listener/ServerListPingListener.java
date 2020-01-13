package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.universal.AttackType;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        try {
            AttackManager.handleAttack(AttackType.PING);
            if (AttackManager.getPingPerSecond() > Config.pingSpeed) {
                e.setMotd("");
                e.setMaxPlayers(0);
            }
        } catch (Exception ex) {
            Logger.throwException(ex);
        }
    }
}
