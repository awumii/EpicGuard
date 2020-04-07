package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.EpicGuardBungee;
import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.detection.AttackManager;
import me.ishift.epicguard.common.detection.Detection;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PreLoginListener implements Listener {
    @EventHandler
    public void onPreLogin(PreLoginEvent event) {
        final PendingConnection connection = event.getConnection();
        final String address = connection.getAddress().getAddress().getHostAddress();
        final String name = connection.getName();

        final Detection detection = new Detection(address, name);
        if (detection.isDetected()) {
            event.setCancelled(true);
            event.setCancelReason(TextComponent.fromLegacyText(detection.getReason().getMessage()));

            EpicGuardBungee.getInstance().getProxy().getPlayers().stream()
                    .filter(player -> EpicGuardBungee.getInstance().getStatusPlayers().contains(player.getUniqueId()))
                    .forEach(player -> BungeeUtil.sendActionBar(player, Messages.prefix + " &7CPS: &c" + AttackManager.getConnectPerSecond() + "/s &8| &6" + name + " &8[&e" + address + "&8] - &7" + detection.getReason()));
        }
    }
}
