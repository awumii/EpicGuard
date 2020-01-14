package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.util.MessagesBukkit;
import me.ishift.epicguard.universal.Config;
import me.ishift.epicguard.universal.util.ChatUtil;
import me.ishift.epicguard.universal.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String cmd = event.getMessage();
        final String[] args = cmd.split(" ");

        // OP Protection module.
        if (Config.opProtectionEnable) {
            if (!Config.opProtectionList.contains(player.getName())) {
                if (player.isOp()) {
                    event.setCancelled(true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Config.opProtectionCommand.replace("{PLAYER}", player.getName()));
                    Bukkit.broadcast(ChatUtil.fix(Config.opProtectionAlert.replace("{PLAYER}", player.getName())), "epicguard.protection.notify");
                    Logger.info("Player " + player.getName() + " has been banned for OP_PROTECTION (Force-OP) detection! (" + cmd + ")");
                    return;
                }
                if (player.hasPermission("experimentalpex.detection") && Config.pexProtection) {
                    event.setCancelled(true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Config.opProtectionCommand.replace("{PLAYER}", player.getName()));
                    Bukkit.broadcast(ChatUtil.fix(Config.opProtectionAlert.replace("{PLAYER}", player.getName())), "epicguard.protection.notify");
                    Logger.info("Player " + player.getName() + " has been banned for OP_PROTECTION_PEX_EXPERIMENTAL (Force-OP-PEX) detection! (" + cmd + ")");
                    return;
                }
            }
        }

        // Allowed Commands module.
        if (Config.allowedCommandsEnable) {
            if (!player.hasPermission(Config.allowedCommandsBypass)) {
                if (!Config.allowedCommands.contains(args[0])) {
                    event.setCancelled(true);
                    player.sendMessage(ChatUtil.fix(MessagesBukkit.NOT_ALLOWED_COMMAND));
                    Logger.info("Player " + player.getName() + " tried to use command " + cmd + " but has no permission for it!");
                    return;
                }
            }
        }

        // Blocked Commands module.
        if (Config.blockedCommandsEnable) {
            if (Config.blockedCommands.contains(args[0])) {
                event.setCancelled(true);
                player.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + MessagesBukkit.BLOCKED_COMMAND));
                Logger.info("Player " + player.getName() + " tried to use forbidden command! (" + cmd + ")");
            }
        }
    }
}
