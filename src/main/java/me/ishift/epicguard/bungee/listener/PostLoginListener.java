package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.EpicGuardBungee;
import me.ishift.epicguard.core.EpicGuard;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class PostLoginListener implements Listener {
    private final EpicGuardBungee plugin;
    private final EpicGuard epicGuard;

    public PostLoginListener(EpicGuardBungee plugin, EpicGuard epicGuard) {
        this.plugin = plugin;
        this.epicGuard = epicGuard;
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        this.epicGuard.getUserManager().addUser(player.getUniqueId());

        ProxyServer.getInstance().getScheduler().schedule(this.plugin, () -> {
            if (player.isConnected()) {
                epicGuard.getStorageManager().whitelist(player.getAddress().getAddress().getHostAddress());
            }
        }, this.epicGuard.getConfig().autoWhitelistTime, TimeUnit.SECONDS);
    }
}
