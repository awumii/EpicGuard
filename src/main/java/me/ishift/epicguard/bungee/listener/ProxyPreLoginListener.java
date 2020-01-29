package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.bungee.util.ConnectionCloser;
import me.ishift.epicguard.bungee.util.FirewallManager;
import me.ishift.epicguard.universal.AttackType;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.KickReason;
import me.ishift.epicguard.universal.check.GeoCheck;
import me.ishift.epicguard.universal.check.NameContainsCheck;
import me.ishift.epicguard.universal.check.ProxyCheck;
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
        final String country = GeoAPI.getCountryCode(event.getConnection().getAddress().getAddress());
        BungeeAttack.handle(AttackType.CONNECT);

        if (FirewallManager.whiiteList.contains(adress)) {
            return;
        }

        if (FirewallManager.blackList.contains(adress)) {
            ConnectionCloser.close(connection, KickReason.BLACKLIST);
            return;
        }

        if (NameContainsCheck.check(connection.getName())) {
            ConnectionCloser.close(connection, KickReason.NAMECONTAINS);
            FirewallManager.blacklist(adress);
            return;
        }

        if (GeoCheck.check(country)) {
            ConnectionCloser.close(connection, KickReason.GEO);
            FirewallManager.blacklist(adress);
            return;
        }

        if (!Config.antibot) {
            return;
        }

        if (BungeeAttack.isAttack()) {
            ConnectionCloser.close(connection, KickReason.ATTACK);
            return;
        }

        if (BungeeAttack.getConnectionPerSecond() > Config.connectSpeed) {
            BungeeAttack.setAttack(true);
            ConnectionCloser.close(connection, KickReason.ATTACK);
            return;
        }

        if (ProxyCheck.check(adress)) {
            ConnectionCloser.close(connection, KickReason.PROXY);
            FirewallManager.blacklist(adress);
        }
    }
}
