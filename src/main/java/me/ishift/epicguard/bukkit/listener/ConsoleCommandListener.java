package me.ishift.epicguard.bukkit.listener;

import me.ishift.epicguard.common.data.config.Messages;
import me.ishift.epicguard.common.data.config.SpigotSettings;
import me.ishift.epicguard.common.util.MessageHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;

public class ConsoleCommandListener implements Listener {
    @EventHandler
    public void onConsoleCommand(ServerCommandEvent event) {
        final String cmd = event.getCommand();

        if (SpigotSettings.disableOperatorMechanicsConsole && (cmd.startsWith("/op") || cmd.startsWith("/deop") || cmd.startsWith("/minecraft:op") || cmd.startsWith("/minecraft:deop"))) {
            event.setCancelled(true);
            event.getSender().sendMessage(MessageHelper.color(Messages.operatorDisabled));
        }
    }
}
