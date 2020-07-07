package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.core.EpicGuard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final EpicGuardBukkit plugin;
    private final EpicGuard epicGuard;

    public PlayerJoinListener(EpicGuardBukkit plugin, EpicGuard epicGuard) {
        this.plugin = plugin;
        this.epicGuard = epicGuard;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.epicGuard.getUserManager().addUser(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
            if (player.isOnline()) {
                epicGuard.getStorageManager().whitelist(player.getAddress().getAddress().getHostAddress());
            }
        }, this.epicGuard.getConfig().autoWhitelistTime * 20L);
    }
}
