package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.AttackSpeed;
import me.ishift.epicguard.universal.check.ServerListCheck;
import me.ishift.epicguard.universal.types.AttackType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerListPingListener implements Listener {

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        ServerListCheck.addAddress(event.getAddress().getHostAddress());
        AttackSpeed.increase(AttackType.PING);
        Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> AttackSpeed.decrease(AttackType.PING), 20L);

        if (AttackSpeed.getPingPerSecond() > Config.pingSpeed) {
            if (Config.bandwidthOptimizer) {
                event.setMotd("");
                event.setMaxPlayers(0);
            }
        }
    }
}
