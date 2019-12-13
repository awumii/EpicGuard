package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.manager.AttackManager;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.manager.DataFileManager;
import pl.polskistevek.guard.bukkit.manager.UserManager;
import pl.polskistevek.guard.bukkit.object.User;
import pl.polskistevek.guard.bukkit.util.MessagesBukkit;
import pl.polskistevek.guard.bukkit.util.Notificator;
import pl.polskistevek.guard.bukkit.util.Updater;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.utils.Logger;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        UserManager.addUser(p);
        if (p.hasPermission(BukkitMain.PERMISSION)) {
            Updater.notify(p);
        }
        User u = UserManager.getUser(p);
        AttackManager.check(AttackManager.AttackType.JOIN);
        PreLoginListener.remove(2);
        Updater.notify(p);
        if (DataFileManager.license.equals("mikecraft")) {
            p.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + "&7This server has &6premium partner license&7! (Licensed to: &6mikecraft&7)"));
        }
        String adress = p.getAddress().getAddress().getHostAddress();
        List<String> history = DataFileManager.get().getStringList("history." + p.getName());
        if (!history.contains(adress)) {
            if (!history.isEmpty()) {
                Notificator.broadcast(MessagesBukkit.HISTORY_NEW.replace("{NICK}", p.getName()).replace("{IP}", adress));
            }
            history.add(adress);
        }
        DataFileManager.get().set("history." + p.getName(), history);
        u.setAdresses(history);
        if (BukkitMain.AUTO_WHITELIST) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p.isOnline()) {
                        if (!BlacklistManager.checkWhitelist(adress)) {
                            Logger.info("Player " + p.getName() + " (" + adress + ") has been whitelisted.", false);
                            BlacklistManager.addWhitelist(adress);
                        }
                    }
                }
            }.runTaskLater(BukkitMain.getPlugin(BukkitMain.class), BukkitMain.AUTO_WHITELIST_TIME);
        }
    }
}
