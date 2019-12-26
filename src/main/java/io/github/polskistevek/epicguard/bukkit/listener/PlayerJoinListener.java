package io.github.polskistevek.epicguard.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import io.github.polskistevek.epicguard.bukkit.GuardBukkit;
import io.github.polskistevek.epicguard.bukkit.manager.AttackManager;
import io.github.polskistevek.epicguard.bukkit.manager.BlacklistManager;
import io.github.polskistevek.epicguard.bukkit.manager.DataFileManager;
import io.github.polskistevek.epicguard.bukkit.manager.UserManager;
import io.github.polskistevek.epicguard.bukkit.object.User;
import io.github.polskistevek.epicguard.bukkit.util.MessagesBukkit;
import io.github.polskistevek.epicguard.bukkit.util.Notificator;
import io.github.polskistevek.epicguard.bukkit.util.Updater;
import io.github.polskistevek.epicguard.utils.Logger;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        try {
            Player p = e.getPlayer();
            String adress = p.getAddress().getAddress().getHostAddress();
            UserManager.addUser(p);
            Updater.notify(p);
            AttackManager.handleAttack(AttackManager.AttackType.JOIN);

            // IP History manager
            if (GuardBukkit.IP_HISTORY_ENABLE) {
                User u = UserManager.getUser(p);
                List<String> history = DataFileManager.get().getStringList("history." + p.getName());

                if (!history.contains(adress)) {
                    if (!history.isEmpty()) {
                        Notificator.broadcast(MessagesBukkit.HISTORY_NEW.replace("{NICK}", p.getName()).replace("{IP}", adress));
                    }
                    history.add(adress);
                }

                DataFileManager.get().set("history." + p.getName(), history);
                u.setAdresses(history);
            }

            // Auto whitelisting
            if (GuardBukkit.AUTO_WHITELIST) {
                Bukkit.getScheduler().runTaskLater(GuardBukkit.getPlugin(GuardBukkit.class), () -> {
                    if (p.isOnline()) {
                        if (!BlacklistManager.checkWhitelist(adress)) {
                            Logger.info("Player " + p.getName() + " (" + adress + ") has been whitelisted.", false);
                            BlacklistManager.addWhitelist(adress);
                        }
                    }
                }, GuardBukkit.AUTO_WHITELIST_TIME);
            }
        } catch (Exception ex) {
            Logger.throwException(ex);
        }
    }
}
