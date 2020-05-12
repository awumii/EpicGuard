package me.ishift.epicguard.bungee.listener;

import me.ishift.epicguard.bungee.util.BungeeUtil;
import me.ishift.epicguard.common.data.config.BungeeSettings;
import me.ishift.epicguard.common.data.config.Messages;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerChatListener implements Listener {
    @EventHandler
    public void onChat(ChatEvent event) {
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        final String[] message = event.getMessage().split(" ");
        if (BungeeSettings.blockedCommandsEnable && BungeeSettings.blockedCommands.contains(message[0])) {
            if (BungeeSettings.blockedCommandsBypass && player.hasPermission("epicguard.bypass.blocked-commands")) {
                return;
            }

            event.setCancelled(true);
            BungeeUtil.sendMessage(player, Messages.prefix + Messages.blockedCommand);
        }
    }
}
