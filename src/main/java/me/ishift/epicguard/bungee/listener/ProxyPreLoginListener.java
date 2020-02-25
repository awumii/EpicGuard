package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.bungee.util.ConnectionCloser;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.check.detection.GeoCheck;
import me.ishift.epicguard.universal.check.detection.NameContainsCheck;
import me.ishift.epicguard.universal.check.detection.ProxyCheck;
import me.ishift.epicguard.universal.types.AttackType;
import me.ishift.epicguard.universal.types.Reason;
import me.ishift.epicguard.universal.util.GeoAPI;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        final PendingConnection connection = event.getConnection();
        final String adress = connection.getAddress().getAddress().getHostAddress();
        final String country = GeoAPI.getCountryCode(connection.getAddress().getAddress());
        BungeeAttack.handle(AttackType.CONNECT);

        if (StorageManager.isWhitelisted(adress)) {
            return;
        }

        if (StorageManager.isBlacklisted(adress)) {
            ConnectionCloser.close(connection, Reason.BLACKLIST);
            return;
        }

        if (NameContainsCheck.check(connection.getName())) {
            ConnectionCloser.close(connection, Reason.NAMECONTAINS);
            StorageManager.blacklist(adress);
            return;
        }

        if (GeoCheck.check(country)) {
            ConnectionCloser.close(connection, Reason.GEO);
            StorageManager.blacklist(adress);
            return;
        }

        if (!Config.antibot) {
            return;
        }

        if (BungeeAttack.isAttack()) {
            ConnectionCloser.close(connection, Reason.ATTACK);
            return;
        }

        if (ProxyCheck.check(adress)) {
            ConnectionCloser.close(connection, Reason.PROXY);
            StorageManager.blacklist(adress);
        }
    }
}
