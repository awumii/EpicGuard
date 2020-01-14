package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.manager.*;
import me.ishift.epicguard.bukkit.object.CustomFile;
import me.ishift.epicguard.bukkit.object.User;
import me.ishift.epicguard.bukkit.util.MessagesBukkit;
import me.ishift.epicguard.bukkit.util.Notificator;
import me.ishift.epicguard.bukkit.util.Updater;
import me.ishift.epicguard.bukkit.util.nms.NMSUtil;
import me.ishift.epicguard.universal.AttackType;
import me.ishift.epicguard.universal.Config;
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
            final Player p = e.getPlayer();
            UserManager.addUser(p);
            final User u = UserManager.getUser(p);

            if (NMSUtil.isOldVersion()) {
                BrandPluginMessageListener.addChannel(p, "MC|BRAND");
            }

            final String adress = p.getAddress().getAddress().getHostAddress();
            u.setIp(adress);

            Updater.notify(p);
            AttackManager.handleAttack(AttackType.JOIN);

            if (Config.autoWhitelist) {
                Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> {
                    if (p.isOnline() && !BlacklistManager.checkWhitelist(adress)) {
                        Logger.info("Player " + p.getName() + " (" + adress + ") has been whitelisted.");
                        BlacklistManager.addWhitelist(adress);
                    }
                }, Config.autoWhitelistTime);
            }

            // IP History manager
            if (Config.ipHistoryEnable) {
                final List<String> history = DataFileManager.getDataFile().getStringList("history." + p.getName());
                if (!history.contains(adress)) {
                    if (!history.isEmpty()) {
                        Notificator.broadcast(MessagesBukkit.HISTORY_NEW.replace("{NICK}", p.getName()).replace("{IP}", adress));
                    }
                    history.add(adress);
                }
                DataFileManager.getDataFile().set("history." + p.getName(), history);
                u.setAdresses(history);
            }

            // Brand Verification
            final CustomFile customFile = FileManager.getFile(GuardBukkit.getInstance().getDataFolder() + "/brand.yml");
            Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> {
                if (!p.isOnline()) {
                    return;
                }

                if (customFile.getConfig().getBoolean("channel-verification.enabled")) {
                    if (u.getBrand().equals("none")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), customFile.getConfig().getString(ChatUtil.fix("channel-verification.punish")).replace("{PLAYER}", p.getName()));
                        Logger.info("Exception occurred in " + p.getName() + "'s connection!");
                        return;
                    }
                }
                if (customFile.getConfig().getBoolean("blocked-brands.enabled")) {
                    for (String string : customFile.getConfig().getStringList("blocked-brands")) {
                        if (u.getBrand().equalsIgnoreCase(string)) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), customFile.getConfig().getString(ChatUtil.fix("blocked-brands.punish")).replace("{PLAYER}", p.getName()));
                        }
                    }
                }
            }, 50L);
        } catch (Exception ex) {
            Logger.throwException(ex);
        }
    }
}
