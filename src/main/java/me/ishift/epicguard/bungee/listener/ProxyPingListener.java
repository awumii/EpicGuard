package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.universal.AttackType;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.GeoAPI;
import me.ishift.epicguard.universal.util.Logger;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        try {
            final String country = GeoAPI.getCountryCode(event.getConnection().getAddress());
            if (Config.ANTIBOT) {
                BungeeAttack.handle(AttackType.PING);
                if (BungeeAttack.getPingPerSecond() > Config.PING_SPEED) {
                    BungeeAttack.setAttack(true);
                }
            }
        } catch (Exception ex) {
            Logger.throwException(ex);
        }
    }
}
