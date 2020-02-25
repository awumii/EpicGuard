package me.ishift.epicguard.bukkit.listener.player;

import me.ishift.epicguard.bukkit.GuardBukkit;
import me.ishift.epicguard.bukkit.listener.server.PluginMessagesListener;
import me.ishift.epicguard.bukkit.manager.AttackManager;
import me.ishift.epicguard.bukkit.manager.User;
import me.ishift.epicguard.bukkit.manager.UserManager;
import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.bukkit.util.misc.Notificator;
import me.ishift.epicguard.bukkit.util.server.Reflection;
import me.ishift.epicguard.bukkit.util.server.Updater;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.StorageManager;
import me.ishift.epicguard.universal.types.AttackType;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        try {
            final Player player = event.getPlayer();
            UserManager.addUser(player);
            final User user = UserManager.getUser(player);
            final String address = user.getAddress();

            AttackManager.handleAttack(AttackType.JOIN);
            // AntiBypass
            if (StorageManager.isBlacklisted(address)) {
                event.setJoinMessage("");
                player.kickPlayer(Messages.messageKickBlacklist.stream().map(s -> ChatUtil.fix(s) + "\n").collect(Collectors.joining()));
                return;
            }

            Updater.notify(player);

            if (Config.autoWhitelist) {
                Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> {
                    if (player.isOnline()) StorageManager.whitelist(address);
                }, Config.autoWhitelistTime);
            }

            // IP History manager
            if (Config.ipHistoryEnable) {
                final List<String> history = StorageManager.getFile().getStringList("address-history." + player.getName());
                if (!history.contains(address)) {
                    if (!history.isEmpty()) {
                        Notificator.broadcast(Messages.historyNew.replace("{NICK}", player.getName()).replace("{IP}", address));
                    }
                    history.add(address);
                }
                StorageManager.getFile().set("address-history." + player.getName(), history);
                user.setAddressList(history);
            }

            // Brand Verification
            if (Reflection.isOldVersion()) {
                PluginMessagesListener.addChannel(player, "MC|BRAND");
                Bukkit.getScheduler().runTaskLater(GuardBukkit.getInstance(), () -> {
                    if (!player.isOnline()) {
                        return;
                    }

                    if (Config.channelVerification) {
                        if (user.getBrand().equals("none")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ChatUtil.fix(Config.channelPunish).replace("{PLAYER}", player.getName()));
                            Logger.info(player.getName() + "has been connection! If you think this is an issue, disable 'channel-verification'. Do NOT report this! This is not a bug!");
                            return;
                        }
                        return;
                    }
                    if (Config.blockedBrands) {
                        for (String string : Config.blockedBrandsValues) {
                            if (user.getBrand().equalsIgnoreCase(string)) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ChatUtil.fix(Config.blockedBrandsPunish).replace("{PLAYER}", player.getName()));
                            }
                        }
                    }
                }, Config.channelDelay);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
