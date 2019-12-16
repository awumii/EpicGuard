package pl.polskistevek.guard.bukkit.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.polskistevek.guard.bukkit.GuardPluginBukkit;
import pl.polskistevek.guard.bukkit.util.MessagesBukkit;
import pl.polskistevek.guard.utils.ChatUtil;
import pl.polskistevek.guard.utils.Logger;

public class PlayerCommandListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        final Player player = event.getPlayer();
        final String cmd = event.getMessage();
        final String[] args = cmd.split(" ");

        // OP Protection module.
        if (GuardPluginBukkit.OP_PROTECTION_ENABLE){
            if (!GuardPluginBukkit.OP_PROTECTION_LIST.contains(player.getName())){
                if (player.isOp()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), GuardPluginBukkit.OP_PROTECTION_COMMAND.replace("{PLAYER}", player.getName()));
                    Bukkit.broadcast(ChatUtil.fix(GuardPluginBukkit.OP_PROTECTION_ALERT), "epicguard.protection.notify");
                    Logger.info("Player " + player.getName() + " has been banned for OP_PROTECTION (Force-OP) detection! (" + cmd + ")", false);
                }
                if (player.hasPermission("experimentalpex.detection") && GuardPluginBukkit.PEX_PROTECTION) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), GuardPluginBukkit.OP_PROTECTION_COMMAND.replace("{PLAYER}", player.getName()));
                    Bukkit.broadcast(ChatUtil.fix(GuardPluginBukkit.OP_PROTECTION_ALERT), "epicguard.protection.notify");
                    Logger.info("Player " + player.getName() + " has been banned for OP_PROTECTION_PEX_EXPERIMENTAL (Force-OP-PEX) detection! (" + cmd + ")", false);
                }
            }
        }

        // Allowed Commands module.
        if (GuardPluginBukkit.ALLOWED_COMMANDS_ENABLE) {
            if (!player.hasPermission(GuardPluginBukkit.ALLOWED_COMMANDS_BYPASS)) {
                if (!GuardPluginBukkit.ALLOWED_COMMANDS.contains(args[0])) {
                    event.setCancelled(true);
                    player.sendMessage(ChatUtil.fix(MessagesBukkit.NOT_ALLOWED_COMMAND));
                    Logger.info("Player " + player.getName() + " tried to use command " + cmd + " but has no permission for it!", false);
                }
            }
        }

        // Blocked Commands module.
        if (GuardPluginBukkit.BLOCKED_COMMANDS_ENABLE){
            if (GuardPluginBukkit.BLOCKED_COMMANDS.contains(args[0])){
                event.setCancelled(true);
                player.sendMessage(ChatUtil.fix(MessagesBukkit.PREFIX + MessagesBukkit.BLOCKED_COMMAND));
                Logger.info("Player " + player.getName() + " tried to use forbidden command! (" + cmd + ")", false);
            }
        }
    }
}
