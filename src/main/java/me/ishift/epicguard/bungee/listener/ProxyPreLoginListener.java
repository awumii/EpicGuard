package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.ConnectionCloser;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.CheckManager;
import me.ishift.epicguard.universal.check.detection.SpeedCheck;
import me.ishift.epicguard.universal.types.AttackType;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class ProxyPreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        final PendingConnection connection = event.getConnection();
        final String address = connection.getAddress().getAddress().getHostAddress();
        final String name = connection.getName();

        SpeedCheck.increase(AttackType.CONNECT);
        ProxyServer.getInstance().getScheduler().schedule(GuardBungee.getInstance(), () -> SpeedCheck.decrease(AttackType.CONNECT), 1, TimeUnit.SECONDS);

        if (StorageManager.isWhitelisted(address)) {
            return;
        }

        CheckManager.getChecks().stream().filter(check -> check.perform(address, name)).forEach(check -> {
            ConnectionCloser.close(connection, check.getReason());
            if (check.shouldBlacklist()) StorageManager.blacklist(address);
        });
    }
}
