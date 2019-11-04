package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.polskistevek.guard.bukkit.BukkitMain;
import pl.polskistevek.guard.bukkit.manager.BlacklistManager;
import pl.polskistevek.guard.bukkit.manager.ConfigManager;
import pl.polskistevek.guard.bukkit.manager.Notificator;
import pl.polskistevek.guard.bukkit.manager.PlayerManager;
import pl.polskistevek.guard.bukkit.object.User;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.utils.Logger;
import pl.polskistevek.guard.utils.Piracy;
import pl.polskistevek.guard.utils.spigot.Updater;
import java.util.List;

public class JoinListener implements Listener {
    public static int jps;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerManager.addUser(p);
        User u = PlayerManager.getUser(p);
        jps++;
        if (jps > 10){
            PreLoginListener.attack = true;
        }
        if (p.getName().equals("PolskiStevek")){
            p.sendMessage(ChatUtil.fix(BukkitMain.PREFIX + "&7Welcome back, developer! This server ID: &e" + Piracy.getServerId() + " &8(&7Premium: &6" + Piracy.b + "&8)"));
        }
        PreLoginListener.rem(2);
        Updater.notify(p);
        String adress = p.getAddress().getAddress().getHostAddress();
        if (adress == null){
            return;
        }
        List<String> history = ConfigManager.get().getStringList("history." + p.getName());
        if (!history.contains(adress)) {
            if (!history.isEmpty()) {
                Notificator.broadcast(BukkitMain.NEW_IP.replace("{NICK}", p.getName()).replace("{IP}", adress));
            }
            history.add(adress);
        }
        ConfigManager.get().set("history." + p.getName(), history);
        u.setAdresses(history);
        if (BukkitMain.AUTO_WHITELIST) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (p.isOnline()) {
                        if (!BlacklistManager.checkWhitelist(adress)) {
                            Logger.log("Player " + p.getName() + " (" + adress + ") has been whitelisted.");
                            BlacklistManager.addWhitelist(adress);
                        }
                        return;
                    }
                    Logger.log("Player " + p.getName() + " (" + adress + ") is offline, can't add to whitelist.");
                }
            }.runTaskLater(BukkitMain.getPlugin(BukkitMain.class), BukkitMain.AUTO_WHITELIST_TIME);
        }
    }
}
