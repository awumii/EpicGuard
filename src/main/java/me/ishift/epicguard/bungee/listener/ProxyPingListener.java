package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.util.BungeeAttack;
import me.ishift.epicguard.universal.AttackType;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.Logger;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {
    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        try {
            if (Config.ANTIBOT) {
                BungeeAttack.handle(AttackType.PING);
                if (BungeeAttack.getPingPerSecond() > Config.PING_SPEED) {
                    BungeeAttack.setAttack(true);
                    final ServerPing response = event.getResponse();
                    response.setDescriptionComponent(new TextComponent(""));
                    event.setResponse(response);
                }
            }
        } catch (Exception ex) {
            Logger.throwException(ex);
        }
    }
}
