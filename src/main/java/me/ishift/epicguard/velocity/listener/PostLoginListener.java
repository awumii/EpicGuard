package me.ishift.epicguard.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.core.handler.JoinHandler;

public class PostLoginListener extends JoinHandler {
    public PostLoginListener(EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();
        this.handle(player.getUniqueId(), player.getRemoteAddress().getAddress().getHostAddress());
    }
}
