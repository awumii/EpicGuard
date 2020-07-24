package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.JoinHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginListener extends JoinHandler implements Listener {
    public PostLoginListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        this.handle(player.getUniqueId(), player.getAddress().getAddress().getHostAddress());
    }
}
