package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.common.data.config.BungeeSettings;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerTabListener implements Listener {
    @EventHandler
    public void onTab(TabCompleteEvent event) {
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        final String message = event.getCursor();

        // Blocking TabComplete
        if (BungeeSettings.tabCompleteBlock) {
            event.setCancelled(true);
            return;
        }

        if (BungeeSettings.customTabComplete) {
            if (BungeeSettings.customTabCompleteBypass && player.hasPermission("epicguard.bypass.custom-tab-complete")) {
                return;
            }

            event.getSuggestions().clear();
            event.getSuggestions().addAll(BungeeSettings.customTabCompleteList);
        }
    }
}
