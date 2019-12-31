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

import java.util.ArrayList;
import java.util.List;

public class PlayerCommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String cmd = event.getMessage();
        final String[] args = cmd.split(" ");

        // OP Protection module.
        if (Config.OP_PROTECTION_ENABLE) {
            if (!Config.OP_PROTECTION_LIST.contains(player.getName())) {
                if (player.isOp()) {
                    event.setCancelled(true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Config.OP_PROTECTION_COMMAND.replace("{PLAYER}", player.getName()));
                    Bukkit.broadcast(ChatUtil.fix(Config.OP_PROTECTION_ALERT.replace("{PLAYER}", player.getName())), "epicguard.protection.notify");
                    Logger.info("Player " + player.getName() + " has been banned for OP_PROTECTION (Force-OP) detection! (" + cmd + ")");
                    return;
                }
                if (player.hasPermission("experimentalpex.detection") && Config.PEX_PROTECTION) {
                    event.setCancelled(true);
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Config.OP_PROTECTION_COMMAND.replace("{PLAYER}", player.getName()));
                    Bukkit.broadcast(ChatUtil.fix(Config.OP_PROTECTION_ALERT.replace("{PLAYER}", player.getName())), "epicguard.protection.notify");
                    Logger.info("Player " + player.getName() + " has been banned for OP_PROTECTION_PEX_EXPERIMENTAL (Force-OP-PEX) detection! (" + cmd + ")");
                    return;
                }
            }
        }

        // Allowed Commands module.
        if (Config.ALLOWED_COMMANDS_ENABLE) {
            if (!player.hasPermission(Config.ALLOWED_COMMANDS_BYPASS)) {
                if (!Config.ALLOWED_COMMANDS.contains(args[0])) {
                    event.setCancelled(true);
                    player.sendMessage(ChatUtil.fix(MessagesBukkit.NOT_ALLOWED_COMMAND));
                    Logger.info("Player " + player.getName() + " tried to use command " + cmd + " but has no permission for it!");
                    return;
                }
            }
        }

        // Blocked Commands module.
        if (Config.BLOCKED_COMMANDS_ENABLE) {
            if (Config.BLOCKED_COMMANDS.contains(args[0])) {
                event.setCancelled(true);
                player.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + MessagesBukkit.BLOCKED_COMMAND));
                Logger.info("Player " + player.getName() + " tried to use forbidden command! (" + cmd + ")");
            }
        }
    }
}
