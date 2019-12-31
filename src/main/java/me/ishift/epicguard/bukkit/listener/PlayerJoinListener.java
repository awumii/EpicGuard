package me.ishift.epicguard.bukkit.listener;

import io.github.polskistevek.epicguard.bukkit.manager.*;
import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.manager.*;
import me.ishift.epicguard.bukkit.object.CustomFile;
import me.ishift.epicguard.bukkit.object.User;
import me.ishift.epicguard.bukkit.util.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.Notificator;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.bukkit.util.nms.NMSUtil;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        try {
            Player p = e.getPlayer();
            if (NMSUtil.isOldVersion()) {
                BrandPluginMessageListener.addChannel(p, "MC|BRAND");
            } else {
                BrandPluginMessageListener.addChannel(p, "MINECRAFT:BRAND");
            }

            String adress = p.getAddress().getAddress().getHostAddress();
            UserManager.addUser(p);
            Updater.notify(p);
            AttackManager.handleAttack(AttackManager.AttackType.JOIN);

            User u = UserManager.getUser(p);
            // IP History manager
            if (GuardBukkit.IP_HISTORY_ENABLE) {
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

            // Brand Verification
            CustomFile customFile = FileManager.getFile(GuardBukkit.dataFolder + "/brand.yml");
            if (customFile.getConfig().getBoolean("channel-verification.enabled")) {
                Bukkit.getScheduler().runTaskLater(GuardBukkit.getPlugin(GuardBukkit.class), () -> {
                    if (p.isOnline()) {
                        if (u.getBrand().equals("none")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), customFile.getConfig().getString(ChatUtil.fix("channel-verification.punish")).replace("{PLAYER}", p.getName()));
                            Logger.info("Exception occurred in " + p.getName() + "'s connection!");
                        }
                        for (String string : customFile.getConfig().getStringList("blocked-brands")) {
                            if (u.getBrand().equalsIgnoreCase(string)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), customFile.getConfig().getString(ChatUtil.fix("blocked-brands.punish")).replace("{PLAYER}", p.getName()));
                            }
                        }
                    }
                }, 20L);
            }

            // Auto whitelisting
            if (GuardBukkit.AUTO_WHITELIST) {
                Bukkit.getScheduler().runTaskLater(GuardBukkit.getPlugin(GuardBukkit.class), () -> {
                    if (p.isOnline()) {
                        if (!BlacklistManager.checkWhitelist(adress)) {
                            Logger.info("Player " + p.getName() + " (" + adress + ") has been whitelisted.");
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
