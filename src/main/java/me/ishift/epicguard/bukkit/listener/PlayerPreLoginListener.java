package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.bukkit.util.ActionBarAPI;
import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.antibot.AttackManager;
import me.ishift.epicguard.common.antibot.Detection;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {
    private final AttackManager attackManager;

    public PlayerPreLoginListener(AttackManager attackManager) {
        this.attackManager = attackManager;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        final String address = event.getAddress().getHostAddress();
        final String name = event.getName();

        final Detection detection = this.attackManager.check(address, name);
        if (detection.isDetected()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, detection.getReason().getMessage());

            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(player -> EpicGuardBukkit.getInstance().getUserManager().getUser(player).isNotifications())
                    .forEach(player -> ActionBarAPI.sendActionBar(player, Messages.prefix + " &7CPS: &c" + this.attackManager.getConnectPerSecond() + "/s &8| &6" + name + " &8[&e" + address + "&8] - &7" + detection.getReason()));
        }
    }
}
