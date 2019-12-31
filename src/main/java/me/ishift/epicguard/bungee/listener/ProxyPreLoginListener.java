package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.GuardBungee;
import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.bungee.util.FirewallManager;
import me.ishift.epicguard.bungee.util.ConnectionCloser;
import me.ishift.epicguard.bungee.util.MessagesBungee;
import me.ishift.epicguard.universal.AttackType;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.check.GeoCheck;
import me.ishift.epicguard.universal.check.ProxyCheck;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.KickReason;
import me.ishift.epicguard.universal.util.Logger;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        try {
            final PendingConnection connection = event.getConnection();
            final String adress = connection.getAddress().getAddress().getHostAddress();
            final String country = GeoAPI.getDatabase().country(connection.getAddress().getAddress()).getCountry().getIsoCode();
            BungeeAttack.handle(AttackType.CONNECT);

            if (FirewallManager.whiiteList.contains(adress)) {
                return;
            }

            if (FirewallManager.blackList.contains(adress)) {
                ConnectionCloser.close(connection, KickReason.BLACKLIST);
                return;
            }

            if (GeoCheck.check(country)) {
                ConnectionCloser.close(connection, KickReason.GEO);
                FirewallManager.blacklist(adress);
                return;
            }

            if (!Config.ANTIBOT) {
                return;
            }

            if (BungeeAttack.isAttack()) {
                ConnectionCloser.close(connection, KickReason.ATTACK);
                return;
            }

            if (BungeeAttack.getConnectionPerSecond() > Config.CONNECT_SPEED) {
                BungeeAttack.setAttack(true);
                ConnectionCloser.close(connection, KickReason.ATTACK);
                return;
            }

            if (ProxyCheck.check(adress)) {
                ConnectionCloser.close(connection, KickReason.PROXY);
                FirewallManager.blacklist(adress);
            }
        } catch (Exception ex) {
            Logger.throwException(ex);
        }
    }
}
