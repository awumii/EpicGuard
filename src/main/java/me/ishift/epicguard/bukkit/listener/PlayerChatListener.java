package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.bukkit.EpicGuardBukkit;
import me.ishift.epicguard.common.types.Reason;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        // MCSpam bots sends these messages to the chat after connect.
        final String message = event.getMessage();
        if (message.equals("'Attack") || message.equals("'/login") || message.equals("'/register")) {
            event.setCancelled(true);
            // Bukkit can't kick player in the async thread...
            Bukkit.getScheduler().runTask(EpicGuardBukkit.getInstance(), () -> event.getPlayer().kickPlayer(Reason.BOT_BEHAVIOUR.getMessage()));
        }
    }
}
