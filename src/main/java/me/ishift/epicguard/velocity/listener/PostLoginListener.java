package me.ishift.epicguard.velocity.listener;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.proxy.Player;
import me.ishift.epicguard.core.EpicGuard;
import me.ishift.epicguard.velocity.EpicGuardVelocity;

import java.util.concurrent.TimeUnit;

public class PostLoginListener {
    private final EpicGuardVelocity plugin;
    private final EpicGuard epicGuard;

    public PostLoginListener(EpicGuardVelocity plugin, EpicGuard epicGuard) {
        this.plugin = plugin;
        this.epicGuard = epicGuard;
    }

    @Subscribe
    public void onPostLogin(PostLoginEvent event) {
        Player player = event.getPlayer();

        this.plugin.getServer().getScheduler().buildTask(this.plugin, () -> {
            if (player.isActive()) {
                epicGuard.getStorageManager().whitelist(player.getRemoteAddress().getAddress().getHostAddress());
            }
        }).delay(this.epicGuard.getConfig().autoWhitelistTime, TimeUnit.SECONDS).schedule();
    }
}
