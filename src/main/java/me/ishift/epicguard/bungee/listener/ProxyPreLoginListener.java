package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.bungee.util.ConnectionCloser;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.CheckManager;
import me.ishift.epicguard.universal.types.AttackType;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        final PendingConnection connection = event.getConnection();
        final String address = connection.getAddress().getAddress().getHostAddress();
        final String name = connection.getName();
        BungeeAttack.handle(AttackType.CONNECT);

        if (StorageManager.isWhitelisted(address)) {
            return;
        }

        CheckManager.getChecks().stream().filter(check -> check.perform(address, name)).forEach(check -> {
            ConnectionCloser.close(connection, check.getReason());
            if (check.shouldBlacklist()) StorageManager.blacklist(address);
        });
    }
}
